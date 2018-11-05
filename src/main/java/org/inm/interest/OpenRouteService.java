package org.inm.interest;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class OpenRouteService {

	private static final Logger log = LoggerFactory.getLogger(OpenRouteService.class);

	private String API_KEY;

	OpenRouteService() {
		API_KEY = System.getProperty("OPEN_ROUTE_SERVICE_API_KEY");
	}

	Location getLocation(String cityName) {
		try {
			Navigateable found = geocodeSearch(cityName);
			List<Double> coordinates = found.navigate("features").navigate("0").navigate("geometry")
					.navigate("coordinates").getCollection();
			return new Location(cityName, coordinates.get(0), coordinates.get(1));
		} catch (IOException e) {
			log.error("can not search for the city:=" + cityName, e);
			return new Location(cityName, 0.0, 0.0);
		}
	}

	Navigateable geocodeSearch(String cityName) throws JsonParseException, JsonMappingException, IOException {
		String urlString = "http://api.openrouteservice.org/geocode/search?&api_key=" + API_KEY + "&text=" + cityName
				+ "&boundary.country=DE&size=1";
		URL url = new URL(urlString);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Object collection = objectMapper.readValue(url.openStream(), new TypeReference<Map<String, Object>>() {
		});
		return new Navigateable(collection);
	}

}
