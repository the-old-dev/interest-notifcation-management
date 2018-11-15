package org.inm.interest.ors;

import org.inm.interest.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;

public class RemoteGeocodeSearchTestCase extends AbstractGeocodeSearchTestCase {
	
	private static final Logger log = LoggerFactory.getLogger(RemoteGeocodeSearchTestCase.class);
	
	@TestConfiguration
	static class TestContextConfiguration extends AbstractTestContextConfiguration {
	}
	
	@Before
	public void check() {
		// No checks necs
	}

	@Test
	public void testRemoteLocationService() throws Exception {
		
		if (!isAPI_KEYPresent()) {
			log.info("Test is not run, because No API KEY ist provided!");
			return;
		}
	
		/**
		 * Test the Location of Heidelberg
		 */
	
		RemoteGeocodeSearch service = new RemoteGeocodeSearch(System.getProperty("OPEN_ROUTE_SERVICE_API_KEY"));
	
		String cityName = "Heidelberg";
		Location location = service.getLocation(cityName, "DE");
	
		Assert.assertNotNull(location);
		Assert.assertEquals(cityName, location.getName());
		Assert.assertEquals(8.681247, location.getLongitude(), 0.0000001);
		Assert.assertEquals(49.412388, location.getLatitude(), 0.0000001);
	
	}

}
