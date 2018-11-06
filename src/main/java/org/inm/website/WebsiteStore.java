package org.inm.website;

import org.inm.store.AbstractStore;
import org.springframework.stereotype.Component;

@Component
public class WebsiteStore extends AbstractStore<Website> {

	public WebsiteStore(boolean inMemory) {
		super(inMemory);
	}

	public WebsiteStore() {
		this(false);
	}

	@Override
	protected Class<Website> getStoreClass() {
		return Website.class;
	}

}
