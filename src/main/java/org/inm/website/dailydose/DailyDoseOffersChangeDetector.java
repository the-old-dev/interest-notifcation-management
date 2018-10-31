package org.inm.website.dailydose;

import org.inm.interest.Interest;
import org.inm.website.AbstractChangeDetector;

public class DailyDoseOffersChangeDetector extends AbstractChangeDetector {

	static long A_DAY = 1000 * 60 * 60 * 24;

	public DailyDoseOffersChangeDetector() {
		super();
		this.setReader(new DailyDoseOffersReader());
		this.setTransformator(new DailyDoseOffersTransformator());
	}

	protected boolean stop(Interest readed, Interest existing) {
		long age = readed.getLastUpdated() - existing.getLastUpdated();
		return age > (3 * A_DAY);
	}

}