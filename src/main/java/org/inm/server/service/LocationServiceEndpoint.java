package org.inm.server.service;

import java.util.List;

import org.inm.interest.Location;
import org.inm.interest.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationServiceEndpoint {

	@Autowired
	LocationService service;

	/**
	 * Reacts to URLs like: http://localhost:8080/api/locationservice/?name=Heidelberg
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/api/locationservice")
	public List<Location> location(@RequestParam(name = "name") String name) {

		return service.getLocations(name);
	}

}
