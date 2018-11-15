package org.inm.server.service;

import java.util.List;

import org.inm.interest.Location;
import org.inm.interest.LocationStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationEndpoint {
    
    @Autowired
	LocationStore store;

	@RequestMapping("/api/location")
	public List<Location> location() {
		return store.findAllAsList();
	}

	/**
	 * Reacts to URLs like: http://localhost:8080/api/location/?detectedOn.subCategory="Foils"
	 * @param requestParameter
	 * @return
	 */
	@RequestMapping("/api/location/unlocated")
	public List<Location> locationUnlocated() {
		try {
			return store.findUnlocated();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
