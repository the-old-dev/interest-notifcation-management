package org.inm.interest;

import org.inm.store.AbstractStore;

public class InterestStore extends AbstractStore<Interest> {

	public InterestStore(boolean inMemory) {
		super(inMemory);
	}

	@Override
	protected Class<Interest> getStoreClass() {
		return Interest.class;
	}

}
