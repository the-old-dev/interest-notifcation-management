package org.inm.interest.ors;

import org.inm.interest.Location;
import org.inm.interest.ors.fuzzy.CountryToken;
import org.junit.Assert;
import org.junit.Test;

public class CountriedGeocodeSearchTestCase {
	
	@Test
	public void test() throws Exception {
		String[] countryCodes = new String[]{
				
				CountryToken.AUSTRIA,
				CountryToken.BELGIUM,
				CountryToken.FRANCE,
				CountryToken.GERMANY,
				CountryToken.ITALY,
				CountryToken.Netherlands,
				CountryToken.SWEDEN,
				CountryToken.SWISS
				
				
		};
		CountriedGeocodeSearch search = new CountriedGeocodeSearch();
		
		for (String countryCode : countryCodes) {
			Location location = search.getLocation(null, countryCode);
			Assert.assertNotNull(location);
			Assert.assertFalse(location.isUnlocated());
		}
	}

}
