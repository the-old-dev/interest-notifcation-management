package org.inm.website;

import org.inm.website.AbstractConfiguration;

public class DummyConfigurationTestCase extends AbstractConfigurationTestCase {

	@Override
	protected AbstractConfiguration getConfiguration() {
		return new DummyConfiguration();
	}

}
