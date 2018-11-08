package org.inm.interest.ors;

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
public class OpenRouteServiceLocationService implements LocationService {

	@Value("${OPEN_ROUTE_SERVICE_API_KEY}")
	private String API_KEY;

	@Autowired
	private LocationStore locationStore;
	
	private LocationService internalServiceChain;
	
	@PostConstruct
    void initialize() {
        this.internalServiceChain = 
            new IntelligentLocationService(
                new LocalizedLocationService(
                    new CachedLocationService(
                        new RemoteLocationService(API_KEY), 
                        locationStore
                    )
                ),
                locationStore
            );        
    }

	public Location getLocation(String name) {

		// return empty
		if (EmtyCheck.isEmpty(name)) {
			return null;
		}

		return this.internalServiceChain.getLocation(name);
	}

}
