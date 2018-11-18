package org.inm.interest.ors;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.inm.interest.Location;
import org.inm.interest.LocationService;
import org.inm.interest.LocationStore;
import org.inm.util.EmtyCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * https://openrouteservice.org/dev/#/api-docs
 * 
 * @author user
 *
 */
@Service
public class OpenRouteServiceGeocodeSearch implements LocationService {

	@Value("${OPEN_ROUTE_SERVICE_API_KEY}")
	private String API_KEY;

	@Autowired
	private LocationStore locationStore;

	private IntelligentGeocodeSearch internalServiceChain;

	@PostConstruct
	void initialize() {

		// check if the api key is present
		if (!hasApiKey()) {
			return;
		}

		// build the chain
		this.internalServiceChain = new IntelligentGeocodeSearch(locationStore);
		
		internalServiceChain.append(new BugfixingGeocodeSearch())
			.append(new LocalizedGeocodeSearch())
				.append(new CachedGeocodeSearch(locationStore))
					.append(new CountriedGeocodeSearch())
						.append(new RemoteGeocodeSearch(API_KEY));

	}

	private boolean hasApiKey() {
		if (API_KEY == null || "".equals(API_KEY) || "${OPEN_ROUTE_SERVICE_API_KEY}".equals(API_KEY)) {
			return false;
		} else {
			return true;
		}
	}

	public List<Location> getLocations(String name) {

		// check if the api key is present
		if (!hasApiKey()) {
			return new ArrayList<Location>();
		}

		// return empty
		if (EmtyCheck.isEmpty(name)) {
			return new ArrayList<Location>();
		}

		return this.internalServiceChain.getLocations(name);
	}

}
