package org.inm.interest.ors;

import java.util.HashMap;
import java.util.Map;

import org.inm.interest.Location;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;

public class CachedGeocodeSearchTestCase extends AbstractGeocodeSearchTestCase {

	@TestConfiguration
	static class TestContextConfiguration extends AbstractTestContextConfiguration {
	}
	
	@Test
	public void testCachedLocationService() throws Exception {
		
		Assert.assertTrue(this.locationStore.isInMemory());
	
		/**
		 * Test localisation:
		 * 
		 * 1) do not cache unlocated responses
		 * 
		 * 2) cache located ones
		 * 
		 * 3) return cached ones
		 * 
		 */
	
		Map<String, Location> responses = new HashMap<String, Location>();
	
		// Unlocated
		responses.put("a", new Location("a", null, null));
	
		// Located
		responses.put("b", new Location("b", 1.0, 1.0));
	
		// create servic chain
		MockGeocodeSearch mock = new MockGeocodeSearch(responses);
		CachedGeocodeSearch service = new CachedGeocodeSearch(locationStore);
		service.append(mock);
	
		// Tests 1) do not cache unlocated responses
		Location testResponse = service.getLocation("a", "DE");
		Assert.assertEquals("a", testResponse.getName());
		Assert.assertEquals(locationStore.findByIdField("a"), null);
	
		// Tests 2) cache located ones
		testResponse = service.getLocation("b", "DE");
		Assert.assertEquals("b", testResponse.getName());
		Assert.assertNotNull(locationStore.findByIdField("b"));
	
		// Tests 3) return cached ones
		testResponse = service.getLocation("b", "DE");
		Assert.assertEquals("b", testResponse.getName());
		Assert.assertEquals(2, mock.getCalls());
	
	}

}
