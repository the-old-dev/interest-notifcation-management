package org.inm.interest.ors;

import org.inm.interest.Location;

public class PushbackGeocodeSearch extends LinkedGeocodeSearch {
	
	@Override
	public Location getLocation(String name, String countryCode) {
		return new Location(name, null, null);
	}

}
