package org.inm.website.dailydose;

import java.util.Iterator;

import org.inm.interest.Interest;
import org.inm.interest.InterestStore;
import org.inm.website.Website;
import org.inm.website.WebsiteStore;
import org.inm.website.dailydose.Configuration;
import org.inm.website.dailydose.DailyDoseOffersChangeDetector;
import org.junit.Assert;
import org.junit.Test;

public class DailyDoseOffersChangeDetectorTestcase {

	@Test
	public void testOffersChangeDetection() throws Exception {

		// Create dependencies
		WebsiteStore websiteStore = new WebsiteStore(true);
		InterestStore interestStore = new InterestStore(true);
		
		// Run Configuration
		Configuration configuration = new Configuration();
		configuration.setStore(websiteStore);
		configuration.run();
		
		// check Configuration
		Website website = websiteStore.findAll().iterator().next();
		Assert.assertNotNull(website);
		// The test configuration should only contain one subsribable
		Assert.assertEquals(1, website.getSubscribables().size());
		
		// Create Detector
		DailyDoseOffersChangeDetector changeDetector = new DailyDoseOffersChangeDetector();
		changeDetector.initialize(interestStore, website, website.getSubscribables().get(0));
		
		// Run the detector
		changeDetector.run();
		
		// get the found interests
		Iterator<Interest> interests = interestStore.findAll().iterator();
		
		// assert at least one found interest
		Assert.assertTrue(interests.hasNext());
		
		// check filled attributes of each interest
		while (interests.hasNext()) {
			Interest interest = interests.next();
			
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
			Assert.assertNotNull(interest.getDetails().get("location"));
			Assert.assertNotNull(interest.getDetails().get("imageURL"));
			
		}
		
	}
	
	
}
