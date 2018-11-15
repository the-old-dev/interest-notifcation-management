package org.inm.interest.ors;

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

	public Location getLocation(String name) {

		// Create search phrases and search
		List<SearchPhrase> searchPhrases = FuzzyLogic.createFuzzySearchPhrases(name);
		for (SearchPhrase searchPhrase : searchPhrases) {
			Location localized = getLocation(searchPhrase.getPhrase(), searchPhrase.getCountryCode());
			if ((localized != null) && (!localized.isUnlocated())) {
				saveInCache(name, localized);
				return localized;
			}
		}
		
		// search again but without the country code
		for (SearchPhrase searchPhrase : searchPhrases) {
			Location localized = getLocation(searchPhrase.getPhrase(), null);
			if ((localized != null) && (!localized.isUnlocated())) {
				saveInCache(name, localized);
				return localized;
			}
		}
		


		// No intelligence helped, save and return an unlocalized location
		Location unlocalized = new Location(name, null, null);
		saveInCache(name, unlocalized);
		return unlocalized;

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
