package org.inm.interest;

import org.inm.server.ApplicationContextConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class OpenRouteServiceTestCase {

	@ComponentScan("org.inm.interest")
	@TestConfiguration
	static class TestContextConfiguration extends ApplicationContextConfiguration {

		@Bean
		public LocationStore locationStore() {
			return new LocationStore(true);
		}

	}

	@Autowired
	LocationStore locationStore;

	@Autowired
	LocationService locationService;

	@Test
	public void testGeocodeSearch() throws Exception {

		if (System.getProperty("OPEN_ROUTE_SERVICE_API_KEY") != null) {
			String cityName = "Heidelberg";

			Location location = locationService.getLocation(cityName);

			Assert.assertNotNull(location);
			Assert.assertEquals(cityName, location.getName());
			Assert.assertEquals(8.681247, location.getLongitude(), 0.0000001);
			Assert.assertEquals(49.412388, location.getLatitude(), 0.0000001);

		}
	}

}
