package org.inm.change.dailydose;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import org.inm.interests.Interest;

public class DailyDoseReadingTestCase {

	@Test
	public void testOffersChangeDetectionChunk() throws Exception {

		// define variables
		DailyDoseOffersChangeDetector changeDetector = new DailyDoseOffersChangeDetector();
		
		// test chunk
		Iterator<Interest> interests = changeDetector.getNextChunk();
		Assert.assertTrue(interests.hasNext());
		
		while (interests.hasNext()) {
			Interest interest = interests.next();
			Assert.assertNotNull(interest);
			Assert.assertNotNull(interest.getCategory());
			Assert.assertNotNull(interest.getSubCategory());
			Assert.assertNotNull(interest.getTitle());
			Assert.assertNotNull(interest.getLastUpdated());
			Assert.assertNotNull(interest.getUrl());
			Assert.assertNotNull(interest.getDetails());
			Assert.assertNotNull(interest.getLastUpdated());
			Assert.assertNotNull(interest.getDetails());
			Assert.assertNotNull(interest.getDetails().get("price"));
			Assert.assertNotNull(interest.getDetails().get("description"));
			Assert.assertNotNull(interest.getDetails().get("date"));
			Assert.assertNotNull(interest.getDetails().get("location"));
			Assert.assertNotNull(interest.getDetails().get("image"));
			Assert.assertNotNull(interest.getDetails().get("imageType"));
		}
		
	}
	
	
}
