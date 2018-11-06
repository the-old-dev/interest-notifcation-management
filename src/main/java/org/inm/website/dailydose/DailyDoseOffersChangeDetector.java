package org.inm.website.dailydose;

import org.inm.interest.Interest;
import org.inm.interest.InterestStore;
import org.inm.interest.LocationService;
import org.inm.website.AbstractChangeDetector;
import org.inm.website.Subscribable;
import org.inm.website.Website;

public class DailyDoseOffersChangeDetector extends AbstractChangeDetector {

	static long A_DAY = 1000 * 60 * 60 * 24;

	public DailyDoseOffersChangeDetector() {
		super();
		this.setReader(new DailyDoseOffersReader());
		this.setTransformator(new DailyDoseOffersTransformator());
	}

	@Override
	public void initialize(InterestStore store, LocationService locationService, Website website,
			Subscribable subscribable) {
		super.initialize(store, locationService, website, subscribable);
		((DailyDoseOffersTransformator) super.transformator).setLocationService(locationService);
	}

	protected boolean stop(Interest readed, Interest existing) {
		long age = readed.getLastUpdated() - existing.getLastUpdated();
		return age > (3 * A_DAY);
	}

}