package org.inm.interest.ors;

import org.inm.interest.Location;
import org.inm.interest.LocationService;
import org.inm.interest.LocationStore;
import org.inm.util.NullCheck;

class IntelligentLocationService implements LocationService {
    
    private LocationService adaptee;
    private LocationStore cache;
    
    public IntelligentLocationService(LocationService adaptee, LocationStore cache) {
        
        NullCheck.NotNull("adaptee", adaptee);
        NullCheck.NotNull("cache", cache);
        
        this.adaptee = adaptee;
        this.cache = cache;
    }

	@Override
	public Location getLocation(String name) {

		String[] searchNames = createIntelligentSearchNames(name);
		
		for (String searchName : searchNames) {
		   	Location localized = adaptee.getLocation(searchName);
			if ( (localized != null) && (!localized.isUnlocated()) ) {
			    saveInCache(name, localized);
				return localized;
			}
		}
		
		// No intelligence helped, save and return an unlocalized location
		Location unlocalized = new Location(null, null, null);
		saveInCache(name, unlocalized);
		return unlocalized;
	
	}
	
	private void saveInCache(String name, Location localized) {
	    
	    Location potentialNew = new Location(name, localized.getLongitude(), localized.getLatitude());
	    
	    Location cached = cache.findByIdField(name);
	    
	    if (cached == null) {
	        cache.insert(potentialNew);
	        return;
	    }
	    
	    if (cached.isUnlocated()) {
	        cache.update(potentialNew);
	        return;
	    } 
	}

	private String[] createIntelligentSearchNames(String name) {
		
		String[] replacementNames = new String[4];
		
		replacementNames[0] = name;
		
		/**
		 * try different replacement algorithms:
		 * 1) a-b => a (b)
		 * 2) a b => a (b)
		 * 3) am => a.
		 */
		replacementNames[1] = name.replace("-", " (")+")";
		replacementNames[2] = name.replace(" ", " (")+")";
		replacementNames[2] = name.replace(" am ", " a. ");
		
		return replacementNames;
	}

}
