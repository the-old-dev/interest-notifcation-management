package org.inm.interest.ors;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.inm.interest.Location;
import org.inm.util.EmtyCheck;
import org.inm.util.NullCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

class RemoteGeocodeSearch extends LinkedGeocodeSearch {

	private static final Logger log = LoggerFactory.getLogger(RemoteGeocodeSearch.class);

	private final RemoteGeocodeSearchThrottle lock = new RemoteGeocodeSearchThrottle();

	private String apiKey;

	RemoteGeocodeSearch(String apiKey) {
		NullCheck.NotNull("apiKey", apiKey);
		this.apiKey = apiKey;
	}

	/**
	 * This call is threadsafe
	 * 
	 * @param cityName,
	 *            must not contain invalid request parameter characters like " "
	 * @return
	 */
	public Location getLocation(String name, String countryCode) {
		
		if (EmtyCheck.isEmpty(name)) {
			return null;
		}

		synchronized (lock) {

			try {

				lock.throttle();

				log.debug("Calling Open Route Service Geocode Search for:=" + name);

				String urlString = "http://api.openrouteservice.org/geocode/search?&api_key=" + apiKey + "&text="
						+ URLEncoder.encode(name, "UTF-8") + "&size=1";

				if (!EmtyCheck.isEmpty(countryCode)) {
					urlString = urlString + "&boundary.country=" + countryCode;
				}

				URL url = new URL(urlString);

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				Object response = objectMapper.readValue(url.openStream(), new TypeReference<Map<String, Object>>() {
				});

				return RemoteGeocodeSearchResponse.extractLocation(response);

			} catch (Exception e) {
				log.error("Error occured during remote service call for name:=" + name, e);
				return null;
			}

		}

	}

	@Override
	public LinkedGeocodeSearch append(LinkedGeocodeSearch linkable) {
		throw new IllegalAccessError("This object is the end of the service chain,  mehtod should not be called.");
	}

}
