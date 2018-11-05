package org.inm.interest;

import org.inm.interest.Location;
import org.inm.interest.OpenRouteService;
import org.junit.Assert;
import org.junit.Test;

public class OpenRouteServiceTestCase {

	@Test
	public void testGeocodeSearch() throws Exception {

		if (System.getProperty("OPEN_ROUTE_SERVICE_API_KEY") != null) {
			String cityName = "Heidelberg";

			Location location = new OpenRouteService().getLocation(cityName);

			Assert.assertNotNull(location);
			Assert.assertEquals(cityName, location.getName());
			Assert.assertEquals(8.681247, location.getLongitude(), 0.0000001);
			Assert.assertEquals(49.412388, location.getLatitude(), 0.0000001);
		
		}
	}

}
