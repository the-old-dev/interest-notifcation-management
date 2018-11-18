package org.inm.interest.ors;

import java.util.ArrayList;
import java.util.List;

import org.inm.interest.Location;
import org.inm.interest.LocationStore;
import org.inm.interest.ors.fuzzy.FuzzyLogic;
import org.inm.interest.ors.fuzzy.SearchPhrase;
import org.inm.util.NullCheck;

class IntelligentGeocodeSearch extends LinkedGeocodeSearch {

	private LocationStore cache;

	public IntelligentGeocodeSearch(LocationStore cache) {
		NullCheck.NotNull("cache", cache);
		this.cache = cache;
	}

	public List<Location> getLocations(String name) {

		List<Location> locations = new ArrayList<Location>();

		// Create search phrases and search
		List<SearchPhrase> searchPhrases = FuzzyLogic.createFuzzySearchPhrases(name);
		for (SearchPhrase searchPhrase : searchPhrases) {
			Location localized = getLocation(searchPhrase);
			if ((localized != null) && (!localized.isUnlocated())) {
				saveInCache(name, localized);
				locations.add(localized);
			}
		}

		if (locations.size() == 0) {
			// No intelligence helped, save and return an unlocalized location
			Location unlocalized = new Location(name, null, null);
			saveInCache(name, unlocalized);
			locations.add(unlocalized);
		}

		return locations;
	}

	private Location getLocation(SearchPhrase searchPhrase) {

		// get with country code
		Location localized = getLocation(searchPhrase.getPhrase(), searchPhrase.getCountryCode());
		if ((localized != null) && (!localized.isUnlocated())) {
			return localized;
		}

		// No try without country code
		localized = getLocation(searchPhrase.getPhrase(), null);
		if ((localized != null) && (!localized.isUnlocated())) {
			return localized;
		}

		// no more chances
		return null;

	}

	@Override
	public Location getLocation(String searchPhrase, String countryCode) {

		return getNextService().getLocation(searchPhrase, countryCode);
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

}
