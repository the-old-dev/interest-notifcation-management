package org.inm.website;

import org.inm.website.AbstractConfiguration;
import org.inm.website.Subscribable;
import org.inm.website.Website;
import org.inm.website.WebsiteStore;
import org.junit.Assert;
import org.junit.Test;

import junit.framework.AssertionFailedError;

public abstract class AbstractConfigurationTestCase {

	
	@Test
	public void testConfiguration() throws Exception {
		
		WebsiteStore store = new WebsiteStore(true);
		AbstractConfiguration configuration = getConfiguration();
		
		configuration.setStore(store);
		configuration.run();
		
		Website configured = null;
		for (Website website: store.findAll()) {
			if (configured !=null) {
				throw new AssertionFailedError("There should be only one Configuration in the store!");
			}
			configured = website;
		}
		
		Assert.assertNotNull(configured);
		for (Subscribable subscribable: configured.getSubscribables()) {
			Assert.assertNotNull(subscribable.getChangeDetectionClass());
		}
	}

	protected abstract AbstractConfiguration getConfiguration();
	
}
