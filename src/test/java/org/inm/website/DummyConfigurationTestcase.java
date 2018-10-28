package org.inm.website;

import org.inm.website.AbstractConfiguration;

public class DummyConfigurationTestcase extends AbstractConfigurationTestcase {

	@Override
	protected AbstractConfiguration getConfiguration() {
		return new DummyConfiguration();
	}

}
