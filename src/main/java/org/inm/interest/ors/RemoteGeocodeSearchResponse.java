package org.inm.interest.ors;

import java.util.ArrayList;
import java.util.List;

import org.inm.interest.Location;

abstract class RemoteGeocodeSearchResponse {

	static Location extractLocation(Object response) {
		return extractLocation(new Navigateable(response));
	}

	private static Location extractLocation(Navigateable found) {

		String locality = 
				found.navigate("features").navigate("0").navigate("properties").navigate("locality").getCollection();
		String name = 
				found.navigate("features").navigate("0").navigate("properties").navigate("name").getCollection();
		List<?> coordinates = 
				found.navigate("features").navigate("0").navigate("geometry").navigate("coordinates").getCollection();

		if (locality != null) {
			return convert(coordinates, locality);
		}

		if (name != null) {
			return convert(coordinates, name);
		}

		return null;
	}

	private static Location convert(List<?> coordinates, String locality) {

		if (locality == null) {
			return null;
		}

		List<Double> converted = convert(coordinates);
		Double longitude = converted.get(0);
		Double latitude = converted.get(1);

		return new Location(locality, longitude, latitude);
	}

	@SuppressWarnings("unchecked")
	private static List<Double> convert(List<?> coordinates) {

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

}
