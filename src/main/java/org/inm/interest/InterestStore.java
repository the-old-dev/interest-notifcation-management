package org.inm.interest;

import org.inm.store.AbstractStore;

public class InterestStore extends AbstractStore<Interest> {

	public InterestStore() {
		this(false);
	}

	public InterestStore(boolean inMemory) {
		super(inMemory);
	}

    public Interest findByIdField(String url) {
		return findByField("url",url).iterator().next();
	}

	@Override
	protected Class<Interest> getStoreClass() {
		return Interest.class;
	}

}
