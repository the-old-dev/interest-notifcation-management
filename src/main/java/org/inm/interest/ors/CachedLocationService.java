package org.inm.interest.ors;

import org.inm.interest.Location;
import org.inm.interest.LocationService;
import org.inm.interest.LocationStore;
import org.inm.util.NullCheck;

class CachedLocationService implements LocationService {
    
    private LocationService adaptee;
    private LocationStore cache;
    
    CachedLocationService(LocationService adaptee, LocationStore cache) {
        
        NullCheck.NotNull("adaptee", adaptee);
        NullCheck.NotNull("cache", cache);
        
        this.adaptee = adaptee;
        this.cache = cache;
        
    }
    
    public Location getLocation(String name) {
        
        Location location = cache.findByIdField(name);
        if (location != null) {
            return location;
        }
        
        location = adaptee.getLocation(name);
        // Only cache located ones while reading
        if(location != null && !location.isUnlocated()) {
            cache(location);
        }
        
        return location;
        
    } 
    
    private void cache(Location location) {
        if (cache.exists(location)) {
            // Do not overwrite with unlocated location
            if (!location.isUnlocated()) {
                cache.update(location);
            }
        } else {
            cache.insert(location);   
        }
    }
}
