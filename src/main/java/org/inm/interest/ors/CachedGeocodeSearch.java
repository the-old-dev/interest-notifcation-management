package org.inm.interest.ors;

import org.inm.interest.Location;
import org.inm.interest.LocationStore;
import org.inm.util.NullCheck;

class CachedGeocodeSearch extends LinkedGeocodeSearch {
    
    private LocationStore cache;
    
    CachedGeocodeSearch(LocationStore cache) {
        NullCheck.NotNull("cache", cache);
        this.cache = cache;     
    }
    
    public Location getLocation(String name, String countryCode) {
        
        Location location = cache.findByIdField(name);
        if (location != null) {
            return location;
        }
        
        location = getNextService().getLocation(name, countryCode);
        
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
