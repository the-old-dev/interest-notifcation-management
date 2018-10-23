package org.inm.website;

import org.inm.store.AbstractStore;

public class WebsiteStore extends AbstractStore<Website> {

	public WebsiteStore(boolean inMemory) {
		super(inMemory);
	}

	@Override
	protected Class<Website> getStoreClass() {
		return Website.class;
	}

}
