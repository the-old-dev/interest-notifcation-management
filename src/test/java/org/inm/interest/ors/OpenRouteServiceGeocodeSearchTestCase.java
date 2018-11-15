package org.inm.interest.ors;

import org.inm.interest.Location;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;

public class OpenRouteServiceGeocodeSearchTestCase extends AbstractGeocodeSearchTestCase {

	private static final Logger log = LoggerFactory.getLogger(OpenRouteServiceGeocodeSearchTestCase.class);

	@TestConfiguration
	static class TestContextConfiguration extends AbstractTestContextConfiguration {
	}

	@Test
	public void testOpenRouteServiceGeocodeSearch() throws Exception {

		if (!isAPI_KEYPresent()) {
			log.info("Test is not run, because No API KEY ist provided!");
			return;
		}

		Assert.assertFalse(this.locationStore.findAll().iterator().hasNext());

		/**
		 * Test different searcht terms
		 * 
		 * 1) "Heidelberg"
		 * 
		 * 2) "St.Peter-Ording"
		 * 
		 */

		// Test 1) "Heidelberg"
		checkWithService("Heidelberg", new Location("Heidelberg", 8.681247, 49.412388));

		// Test 2) "St. Peter-Ording"
		checkWithService("St.Peter-Ording", new Location("St. Peter-Ording", 8.631477, 54.302261));

		// Test 3) "International Searches"
		checkWithService("Paris Frankreich", new Location("Paris", 2.331839, 48.859116));
		checkWithService("Paris", new Location("Paris", 2.331839, 48.859116));
		
		checkWithService("Bern", new Location("Bern", 7.612656, 46.793594));
		checkWithService("Bern schweiz", new Location("Bern", 7.612656, 46.793594));

	}

	private void checkWithService(String name, Location expected) {

		Location location = locationService.getLocation(name);

		Assert.assertNotNull(location);
		Assert.assertEquals(expected.getName(), location.getName());
		Assert.assertEquals(expected.getLongitude(), location.getLongitude(), 0.0000001);
		Assert.assertEquals(expected.getLatitude(), location.getLatitude(), 0.0000001);

	}

}
