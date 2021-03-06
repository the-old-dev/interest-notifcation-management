package org.inm.website.dailydose;

import java.util.Iterator;
import java.util.List;

import org.inm.interest.Interest;
import org.inm.interest.InterestStore;
import org.inm.interest.Location;
import org.inm.interest.LocationService;
import org.inm.interest.LocationStore;
import org.inm.server.ApplicationContextConfiguration;
import org.inm.subscription.SubscriberStore;
import org.inm.website.Website;
import org.inm.website.WebsiteStore;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DailyDoseOffersChangeDetectorTestCase {

	@ComponentScan("org.inm.interest")
	@TestConfiguration
	static class TestContextConfiguration {
		
		@Bean
		public InterestStore interestStore() {
			return new InterestStore(true);
		}

		@Bean
		public WebsiteStore websiteStore() {
			return new WebsiteStore(true);
		}

		@Bean
		public SubscriberStore subscriberStore() {
			return new SubscriberStore(true);
		}

        @Bean
        public LocationStore locationStore() {
        	return new LocationStore(true);
        }
        
	}

	@Autowired
	WebsiteStore websiteStore;

	@Autowired
	InterestStore interestStore;

	@Autowired
	LocationStore locationStore;

	@Autowired
	LocationService locationService;

	@Test
	public void testOffersChangeDetection() throws Exception {

		// Run Configuration
		Configuration configuration = new Configuration();
		configuration.setStore(websiteStore);
		configuration.run();

		// check Configuration
		Website website = websiteStore.findAll().iterator().next();
		Assert.assertNotNull(website);
		// The test configuration should only contain one subsribable
		Assert.assertEquals(1, website.getSubscribables().size());

		// ### Insert Test ###

		// Create Detector
		DailyDoseOffersChangeDetector changeDetector = new DailyDoseOffersChangeDetector();
		changeDetector.initialize(interestStore, locationService, website, website.getSubscribables().get(0));

		// Run the detector
		changeDetector.run();

		// get the found interests
		Iterator<Interest> interests = interestStore.findAll().iterator();

		// assert at least one found interest
		Assert.assertTrue(interests.hasNext());

		// remember the first id for the next test
		String firstId = null;

		// check filled attributes of each interest
		while (interests.hasNext()) {
			Interest interest = interests.next();

			if (firstId == null) {
				firstId = interest.getUrl();
			}

			Assert.assertNotNull(interest);

			Assert.assertNotNull(interest.getUrl());
			Assert.assertNotNull(interest.getDetectedOn());

			Assert.assertNotNull(interest.getTitle());
			Assert.assertNotNull(interest.getLastUpdated());

			Assert.assertNotNull(interest.getDetails());
			Assert.assertNotNull(interest.getDetails());
			Assert.assertNotNull(interest.getDetails().get("price"));
			Assert.assertNotNull(interest.getDetails().get("description"));
			Assert.assertNotNull(interest.getDetails().get("date"));
			List<Location> locations = interest.getLocations();
			Assert.assertNotNull(locations);
			for (Location location : locations) {
				System.out.println(location.toString());
			}

			Assert.assertNotNull(interest.getDetails().get("imageURL"));

		}

		// ### Update test ###

		// update the first elements lastupdated attribuge to 4 days old,
		Interest firstOne = interestStore.findByIdField(firstId);
		firstOne.setLastUpdated(firstOne.getLastUpdated() - (4 * DailyDoseOffersChangeDetector.A_DAY));
		interestStore.update(firstOne);

		// Measure the time
		Thread.sleep(101);
		long meashuredTime = System.currentTimeMillis();

		// run the change Detector again
		changeDetector.run();

		// Expect no updates. Check by an update time older than 100 ms of the
		// meashuredTime
		interests = interestStore.findAll().iterator();
		while (interests.hasNext()) {
			Interest interest = interests.next();
			Assert.assertTrue((meashuredTime - interest.getLastUpdated()) > 100);
		}
	}

}
