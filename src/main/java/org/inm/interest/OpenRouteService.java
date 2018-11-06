package org.inm.interest;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.inm.util.EmtyCheck;

/**
 * https://openrouteservice.org/dev/#/api-docs
 * 
 * @author user
 *
 */
public class OpenRouteService implements LocationService {

	private static final Logger log = LoggerFactory.getLogger(OpenRouteService.class);

	private String API_KEY;
	private LocationStore store;

	public OpenRouteService() {
		API_KEY = System.getProperty("OPEN_ROUTE_SERVICE_API_KEY");
	}

    public void setLocationStore(LocationStore store) {
        this.store = store;
    }

	public Location getLocation(String name) {
	    
	    if (EmtyCheck.isEmpty(name)) {
	        return null;
	    }
	    
	    Iterator<Location> found = this.store.findByField("name",name).iterator();
    	if (!found.hasNext()) {
    		Location location = getLocationInternal(name);
    		this.store.insert(location);
    		found = this.store.findByField("name",name).iterator();
    	}
    	
    	return found.next();
 
    }   

	Location getLocationInternal(String cityName) {
	    
		try {
			
			Navigateable found = geocodeSearch(cityName);
			List<?> coordinates = found.navigate("features").navigate("0").navigate("geometry")
					.navigate("coordinates").getCollection();		
			
			List<Double> converted = convert(coordinates);
			
			Double longitude = converted.get(0);
			Double latitude = converted.get(1);
			
			return new Location(cityName, longitude, latitude);
			
		} catch (Exception e) {
			log.error("can not search for the city:=" + cityName, e);
			return new Location(cityName, 0.0, 0.0);
		}
	}
	
	private List<Double> convert(List<?> coordinates) {
	    
	    List<Double> converted = new ArrayList<Double> ();
	    
	    if (coordinates == null ) {
	        coordinates = new ArrayList<Double> ();
        }

        if (coordinates.size() < 1) {

	        ((ArrayList<Double>) coordinates).add(0.0);
	        
	        ((ArrayList<Double>) coordinates).add(0.0);

	    }
	    
	    
	    for (Object coordinate : coordinates) {
	        if (coordinate instanceof Number) {
	           converted.add(((Number)coordinate).doubleValue());
	        }
	    }
	    return converted;
	}

	/**
	 * @param cityName,
	 *            must not contain invalid request parameter characters like " "
	 * @return
	 */
	Navigateable geocodeSearch(String cityName) throws JsonParseException, JsonMappingException, IOException {
		String urlString = "http://api.openrouteservice.org/geocode/search?&api_key=" + API_KEY + "&text="
				+ URLEncoder.encode(cityName) + "&boundary.country=DE&size=1";
		URL url = new URL(urlString);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Object collection = objectMapper.readValue(url.openStream(), new TypeReference<Map<String, Object>>() {
		});
		return new Navigateable(collection);
	}

}
