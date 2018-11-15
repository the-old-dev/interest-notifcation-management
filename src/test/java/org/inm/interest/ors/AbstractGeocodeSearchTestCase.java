package org.inm.interest.ors;

import org.inm.interest.InterestStore;
import org.inm.interest.LocationService;
import org.inm.interest.LocationStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class AbstractGeocodeSearchTestCase {
	
	@ComponentScan("org.inm.interest")
	@TestConfiguration
	static class AbstractTestContextConfiguration {

		@Bean
		public LocationStore locationStore() {
			return new LocationStore(true);
		}
		
		@Bean
		public InterestStore interestStore() {
			return new InterestStore(true);
		}

	}

	@Autowired
	protected LocationService locationService;
	
	@Autowired
	protected LocationStore locationStore;

	public AbstractGeocodeSearchTestCase() {
		super();
	}

	@Before
	public void check() {
		if (locationStore == null || !locationStore.isInMemory()) {
			Assert.fail("Incorrect initialisation of the location store!");
		}
	}

	protected boolean isAPI_KEYPresent() {
		return System.getProperty("OPEN_ROUTE_SERVICE_API_KEY", null) != null;
	}

}