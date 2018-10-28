package org.inm.website.dailydose;

import org.inm.AfterApplicationStartup;
import org.inm.website.AbstractConfiguration;

@AfterApplicationStartup
public class Configuration extends AbstractConfiguration {

	@Override
	protected Class<?> getDefaultChangeDetectorClass() {
		return DailyDoseOffersChangeDetector.class;
	}

}