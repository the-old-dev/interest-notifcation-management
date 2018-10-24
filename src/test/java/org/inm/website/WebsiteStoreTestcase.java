package org.inm.website;

import org.inm.store.AbstractStore;
import org.inm.store.AbstractStoreTestCase;

public class WebsiteStoreTestcase extends AbstractStoreTestCase<Website> {

	@Override
	protected AbstractStore<Website> createStore() throws Exception {
		return new WebsiteStore(true);
	}

	@Override
	protected Class<Website> getEntityClass() {
		return Website.class;
	}

}
