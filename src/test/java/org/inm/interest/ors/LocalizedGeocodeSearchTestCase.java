package org.inm.interest.ors;

import java.util.HashMap;
import java.util.Map;

import org.inm.interest.Location;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;

public class LocalizedGeocodeSearchTestCase extends AbstractGeocodeSearchTestCase {

	@TestConfiguration
	static class TestContextConfiguration extends AbstractTestContextConfiguration {
	}
	
	@Test
	public void testLocalizedLocationService() throws Exception {
	
		/**
		 * Test localisation:
		 * 
		 * 1) a unlocalized => b localized
		 * 
		 */
	
		Map<String, Location> responses = new HashMap<String, Location>();
	
		// Unlocated
		responses.put("a", new Location("b", null, null));
	
		// Located
		responses.put("b", new Location("b", 1.0, 1.0));
	
		// create service chain
		MockGeocodeSearch mock = new MockGeocodeSearch(responses);
		LocalizedGeocodeSearch service = new LocalizedGeocodeSearch();
		service.append(mock);
	
		// Tests a -> b
		Location testResponse = service.getLocation("a", "DE");
		Assert.assertEquals("b", testResponse.getName());
	
	}

}
