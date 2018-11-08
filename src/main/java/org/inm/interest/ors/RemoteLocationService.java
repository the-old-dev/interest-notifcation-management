package org.inm.interest.ors;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.inm.interest.Location;
import org.inm.interest.LocationService;
import org.inm.util.NullCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RemoteLocationService implements LocationService{    
    
    private static final Logger log = LoggerFactory.getLogger(RemoteLocationService.class);
    
    private final Throttle lock = new Throttle();

    private String apiKey;
    
    RemoteLocationService(String apiKey) {
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
	public Location getLocation (String cityName) {
            
		synchronized (lock) {
		    
		    try {

    			lock.throttle();
    
    			log.info("Calling Open Route Service Geocode Search for:=" + cityName);
    
    			String urlString = "http://api.openrouteservice.org/geocode/search?&api_key=" + apiKey + "&text="
    					+ URLEncoder.encode(cityName, "UTF-8") + "&boundary.country=DE&size=1";
    			URL url = new URL(urlString);
    
    			ObjectMapper objectMapper = new ObjectMapper();
    			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    			Object response 
    			 = objectMapper.readValue(url.openStream(), new TypeReference<Map<String, Object>>() {});
    			return GeocodeSearchResponse.extractLocation(response);
            } catch(Exception e) {
                log.error("Error occured during remote service call for name:="+cityName,e);
                return null;
            }

		}

	}
    
}
