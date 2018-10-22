package org.inm.interests;

import org.inm.store.AbstractStore;

public class InterestsStore extends AbstractStore<Interest> {

	public InterestsStore(boolean inMemory) {
		super(inMemory);
	}

	@Override
	protected Class<Interest> getStoreClass() {
		return Interest.class;
	}

}
