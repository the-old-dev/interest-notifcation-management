package org.inm.interest.ors;

import java.util.HashMap;
import java.util.Map;

import org.inm.interest.Location;
import org.inm.interest.ors.fuzzy.CountryToken;
import org.inm.util.EmtyCheck;

public class CountriedGeocodeSearch extends LinkedGeocodeSearch {
	
	private Map<String, Location> countries = new HashMap<String, Location>();
	
	CountriedGeocodeSearch() {
		countries.put(CountryToken.AUSTRIA, new Location("Österreich", 47.429021, 14.335438));
		countries.put(CountryToken.BELGIUM, new Location("Belgien", 50.651901, 4.723856));
		countries.put(CountryToken.GERMANY, new Location("Deutschland", 50.766445, 10.321038));
		countries.put(CountryToken.Netherlands, new Location("Niederlande", 53.125466, 5.582645));
		countries.put(CountryToken.SWEDEN, new Location("Schweden",62.604541, 15.570948));
		countries.put(CountryToken.SWISS, new Location("Schweiz", 46.680987, 8.121534));
		
		countries.put(CountryToken.ITALY, new Location("Italien", 42.849533, 12.544510));
		countries.put(CountryToken.FRANCE, new Location("Frankreich", 47.284227, 2.360479 ));
	}

	@Override
	protected Location getLocation(String name, String countryCode) {
		if (EmtyCheck.isEmpty(name) && !EmtyCheck.isEmpty(countryCode)) {
			return countries.get(countryCode);
		}
		return getNextService().getLocation(name, countryCode);
	}

}
