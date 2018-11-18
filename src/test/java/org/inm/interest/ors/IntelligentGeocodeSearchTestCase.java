package org.inm.interest.ors;

import java.util.HashMap;
import java.util.Map;

import org.inm.interest.Location;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;

public class IntelligentGeocodeSearchTestCase extends AbstractGeocodeSearchTestCase {

	@TestConfiguration
	static class TestContextConfiguration extends AbstractTestContextConfiguration {}

	@Test
	public void testIntelligentLocationService() throws Exception {

		/**
		 * Test the different replacement algorithms with simple cases:
		 * 
		 * 1) st.a => St. a
		 * 
		 * 2) a-b => a b
		 * 
		 * 3) a am b => a a. b
		 * 
		 * Allways check the expected caching
		 */

		Map<String, Location> responses = new HashMap<String, Location>();

		// Unlocated
		responses.put("st.a", new Location());
		responses.put("a-b", new Location());
		responses.put(" a am b", new Location());
		responses.put("St.Peter-Ording", new Location());

		// Located
		responses.put("St. a", new Location("St. a", 1.0, 1.0));
		responses.put("a b", new Location("a b", 1.0, 1.0));
		responses.put("a a. b", new Location("a a. b", 1.0, 1.0));
		responses.put("St. Peter Ording", new Location("St. Peter Ording", 1.0, 1.0));

		MockGeocodeSearch mock = new MockGeocodeSearch(responses);
		IntelligentGeocodeSearch service = new IntelligentGeocodeSearch(locationStore);

		service.append(mock);

		// Tests for 1) st.a => St. a
		String name = "st.a";
		String expected = "St. a";
		runServiceAndChcek(name, expected, service);

		// Test 2) a-b => a b
		name = "a-b";
		expected = "a b";
		runServiceAndChcek(name, expected, service);

		// Test 3) a am b => a a. b
		name = "a am b";
		expected = "a a. b";
		runServiceAndChcek(name, expected, service);

		// Test 4) St.Peter-Ording
		name = "St.Peter-Ording";
		expected = "St. Peter Ording";
		runServiceAndChcek(name, expected, service);
	}

	private void runServiceAndChcek(String name, String expected, IntelligentGeocodeSearch service) {

		Location testResponse = service.getLocations(name).get(0);
		Assert.assertEquals(expected, testResponse.getName());
		Assert.assertFalse(locationStore.findByIdField(name).isUnlocated());
	}

}
