package org.inm.website;

import org.inm.website.AbstractConfiguration;

public class DummyConfiguration extends AbstractConfiguration {

	@Override
	protected Class<?> getDefaultChangeDetectorClass() {
		return DummyChangeDetector.class;
	}

}
