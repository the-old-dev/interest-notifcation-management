package org.inm.interest.ors;

import org.inm.interest.Location;

class LocalizedGeocodeSearch extends LinkedGeocodeSearch {

	@Override
	public Location getLocation(String name, String countryCode) {

		Location location = getNextService().getLocation(name, countryCode);
		if (location == null) {
			return null;
		}

		if (location.isUnlocated()) {
			location = tryToLocalize(name, location, countryCode);
		}

		return location;
	}

	private Location tryToLocalize(String name, Location location, String countryCode) {

		// Without a name, there is no localization possible
		String locationName = location.getName();
		if (locationName == null) {
			return location;
		}

		// if it is the same name, simply return
		if (name.equals(locationName)) {
			return location;
		}

		// Now try a new search
		return getNextService().getLocation(locationName, countryCode);
		
	}
}
