package org.inm.interest;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.inm.util.EmtyCheck;
import org.inm.util.NullCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * https://openrouteservice.org/dev/#/api-docs
 * 
 * @author user
 *
 */
@Service
public class OpenRouteService implements LocationService {

	private static final Logger log = LoggerFactory.getLogger(OpenRouteService.class);

	private final Object lock = new Object();

	private long lastCalled = 0l;

	@Value("${OPEN_ROUTE_SERVICE_API_KEY}")
	private String API_KEY;

	@Autowired
	private LocationStore locationStore;

	public OpenRouteService() {
	}

	public OpenRouteService(LocationStore locationStore, String apiKey) {

		NullCheck.NotNull("locationStore", locationStore);
		NullCheck.NotNull("apiKey", apiKey);

		this.locationStore = locationStore;
		this.API_KEY = apiKey;
	}

	public Location getLocation(String name) {

		// return empty
		if (EmtyCheck.isEmpty(name)) {
			return null;
		}

		// return from store
		Location storeLocation = getFromStore(name);
		if (storeLocation != null) {
			return storeLocation;
		}

		// get from remote
		Location remoteLocation = getFromRemoteWithReplacementNames(name);

		if (remoteLocation == null) {
			Location unknownLocation = new Location(name, null, null);
			cache(unknownLocation);
			return unknownLocation;
		}

		// cache remote & the handed one
		try {

			// remote
			cache(remoteLocation);

			// handed one
			remoteLocation.setName(name);
			cache(remoteLocation);

		} catch (Exception e) {
			log.error("Error during insert of location!", e);
		}

		return remoteLocation;

	}

	private Location getFromRemoteWithReplacementNames(String name) {
		String[] names = replacementNames(name);
		for (String rName : names) {
			Location localized = getFromRemote(rName);
			if (!isUnlocated(localized)) {
				return localized;
			}
		}
		return null;
	}

	private void cache(Location remoteLocation) {
		Location cached = this.locationStore.findByIdField(remoteLocation.getName());
		if (cached == null) {
			this.locationStore.insert(remoteLocation);
		} else if (isUnlocated(cached)) {
			this.locationStore.update(remoteLocation);
		}
	}

	private boolean isUnlocated(Location remoteLocation) {
		
		if(remoteLocation == null) {
			return true;
		}
		
		return isNullOrZero(remoteLocation.getLatitude()) && isNullOrZero(remoteLocation.getLongitude());
	}

	private boolean isNullOrZero(Double value) {

		if (value == null) {
			return true;
		}

		return value.equals(0.0);

	}

	private Location getFromStore(String name) {
		return this.locationStore.findByIdField(name);
	}

	Location getFromRemote(String cityName) {

		if (API_KEY == null) {
			log.error("Can not search remote for name:=" + cityName + ", No API KEY provided");
			return null;
		}

		try {

			Location location = geocodeSearch(cityName);
			
			if (location == null) {
				return null;
			}

			if (isUnlocated(location)) {
				return tryToLocalize(location);
			}
			return location;
		} catch (Exception e) {
			log.error("can not search for the city:=" + cityName, e);
			return null;
		}
	}

	private Location tryToLocalize(Location location)
			throws JsonParseException, JsonMappingException, IOException, InterruptedException {

		// Without a name, there is no localization possible
		String name = location.getName();
		if (name == null) {
			return location;
		}
		
		return readLocationWithCache(name);
	}
	
	private String[] replacementNames(String name) {
		
		String[] replacementNames = new String[4];
		
		replacementNames[0] = name;
		
		/**
		 * try different replacement algorithms:
		 * 1) a-b => a (b)
		 * 2) a b => a (b)
		 * 3) am => a.
		 */
		replacementNames[1] = name.replace("-", " (")+")";
		replacementNames[2] = name.replace(" ", " (")+")";
		replacementNames[2] = name.replace(" am ", " a. ");
		
		return replacementNames;
	}

	private Location readLocationWithCache(String name)
			throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		if (name == null) {
			return null;
		}

		Location storeLocation = getFromStore(name);
		if (storeLocation != null) {
			return storeLocation;
		}

		return geocodeSearch(name);
	}

	private Location extractLocation(Navigateable found) {

		String locality = found.navigate("features").navigate("0").navigate("properties").navigate("locality")
				.getCollection();
		
		String name = found.navigate("features").navigate("0").navigate("properties").navigate("name")
				.getCollection();

		List<?> coordinates = found.navigate("features").navigate("0").navigate("geometry").navigate("coordinates")
				.getCollection();

		if (locality != null) {
			return convert(coordinates, locality);
		}
		
		if (name != null) {
			return convert(coordinates, name);
		}
		
		return null;
	}

	private Location convert(List<?> coordinates, String locality) {

		if (locality == null) {
			return null;
		}

		List<Double> converted = convert(coordinates);
		Double longitude = converted.get(0);
		Double latitude = converted.get(1);

		return new Location(locality, longitude, latitude);
	}

	private List<Double> convert(List<?> coordinates) {

		List<Double> converted = new ArrayList<Double>();

		if (coordinates == null) {
			coordinates = new ArrayList<Double>();
		}

		if (coordinates.size() < 1) {
			((ArrayList<Double>) coordinates).add(0.0);
			((ArrayList<Double>) coordinates).add(0.0);
		}

		for (Object coordinate : coordinates) {
			if (coordinate instanceof Number) {
				converted.add(((Number) coordinate).doubleValue());
			}
		}
		return converted;
	}

	/**
	 * This call is threadsafe
	 * 
	 * @param cityName,
	 *            must not contain invalid request parameter characters like " "
	 * @return
	 */
	Location geocodeSearch(String cityName)
			throws JsonParseException, JsonMappingException, IOException, InterruptedException {

		synchronized (lock) {

			throttle();

			log.info("Calling Open Route Service Geocode Search for:=" + cityName);

			String urlString = "http://api.openrouteservice.org/geocode/search?&api_key=" + API_KEY + "&text="
					+ URLEncoder.encode(cityName) + "&boundary.country=DE&size=1";
			URL url = new URL(urlString);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Object collection = objectMapper.readValue(url.openStream(), new TypeReference<Map<String, Object>>() {
			});
			return extractLocation(new Navigateable(collection));

		}

	}

	/**
	 * This webservice has a usage plan with a rate limitation:
	 * https://openrouteservice.org/plans/
	 * 
	 * Free Plan: - 2.500 Requests per day - up to 40 Requests per minute
	 * 
	 * This method das a throtteling to 1 request per second.
	 */
	private void throttle() throws InterruptedException {

		long current = System.currentTimeMillis();
		long timeDelta = current - this.lastCalled;

		if (timeDelta < 2000) {
			Thread.sleep(2000 - timeDelta);
		}

		this.lastCalled = System.currentTimeMillis();

	}

}
