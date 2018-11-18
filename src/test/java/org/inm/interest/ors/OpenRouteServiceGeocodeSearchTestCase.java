package org.inm.interest.ors;

import java.util.Iterator;

import org.inm.interest.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;

public class OpenRouteServiceGeocodeSearchTestCase extends AbstractGeocodeSearchTestCase {

	private static final Logger log = LoggerFactory.getLogger(OpenRouteServiceGeocodeSearchTestCase.class);

	@TestConfiguration
	static class TestContextConfiguration extends AbstractTestContextConfiguration {}

	@Before
	public void check() {

		super.check();

		if (!isAPI_KEYPresent()) {
			log.info("Test is not run, because No API KEY ist provided!");
			return;
		}

		Iterator<Location> locations = this.locationStore.findAll().iterator();
		while (locations.hasNext()) {
			this.locationStore.delete(locations.next());
		}

	}

	@Test
	public void testSearchHeidelberg() throws Exception {
		
		if (!isAPI_KEYPresent()) {
			return;
		}

		// Test 1) "Heidelberg"
		checkWithService("Heidelberg", new Location("Heidelberg", 8.681247, 49.412388));

	}

	@Test
	public void testSearchStPeterOrding() throws Exception {
		
		if (!isAPI_KEYPresent()) {
			return;
		}

		// Test 2) "St. Peter-Ording"
		checkWithService("St.Peter-Ording", new Location("St. Peter-Ording", 8.631477, 54.302261));
	}

	@Test
	public void testSearchFrance() throws Exception {

		if (!isAPI_KEYPresent()) {
			return;
		}
		
		// Test 3) "International Searches"
		checkWithService("Frankreich", new Location("Frankreich", 47.284227, 2.360479));
		checkWithService("Paris Frankreich", new Location("Paris", 2.331839, 48.859116));
		checkWithService("Paris", new Location("Paris", 2.331839, 48.859116));
	}

	@Test
	public void testSearchSwiss() throws Exception {

		if (!isAPI_KEYPresent()) {
			return;
		}
		
		checkWithService("Bern", new Location("Bern", 7.612656, 46.793594));
		checkWithService("Bern schweiz", new Location("Bern", 7.612656, 46.793594));

	}

	private void checkWithService(String name, Location expected) {

		if (!isAPI_KEYPresent()) {
			return;
		}
		
		Location location = locationService.getLocations(name).get(0);

		Assert.assertNotNull(location);
		Assert.assertEquals(expected.getName(), location.getName());
		Assert.assertEquals(expected.getLongitude(), location.getLongitude(), 0.0000001);
		Assert.assertEquals(expected.getLatitude(), location.getLatitude(), 0.0000001);

	}

}
