package org.inm.interest.ors;

import org.inm.interest.Location;
import org.inm.interest.LocationService;
import org.inm.util.EmtyCheck;
import org.inm.util.NullCheck;

class LocalizedLocationService implements LocationService {
    
    private LocationService cached;
    
    LocalizedLocationService(CachedLocationService cached) {
        NullCheck.NotNull("cached", cached);
        this.cached = cached;        
    }

	@Override
	public Location getLocation(String name) {
	    
	    if(EmtyCheck.isEmpty(name)) {
	        return null;
	    }
		
		Location location = cached.getLocation(name);
		if (location == null) {
		    return null;
		}
		
		if (location.isUnlocated()) {
		    location = tryToLocalize(name, location);
		}
		
		return location;
	}
	
	private Location tryToLocalize(String name, Location location) {

		// Without a name, there is no localization possible
		String locationName = location.getName();
		if (locationName == null) {
			return location;
		}
		
		// if it is the same name, simply return
		if (name.equals(locationName)) {
		    return location;
		}
		
		// Now try a cached new search
		return cached.getLocation(locationName);
	}
}
