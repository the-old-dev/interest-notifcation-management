package org.inm.change.dailydose;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import org.inm.interests.Interest;

public class DailyDoseReadingTestCase {

	@Test
	public void testOffersChangeDetection() throws Exception {

		// define variables
		DailyDoseOffersChangeDetector changeDetector = new DailyDoseOffersChangeDetector();

		Iterator<Interest> interests = changeDetector.getNextChunk();

		Assert.assertTrue(interests.hasNext());
	}
}
