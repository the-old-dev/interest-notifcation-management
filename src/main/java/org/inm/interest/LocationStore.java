package org.inm.interest;

import java.util.Iterator;

import org.inm.store.AbstractStore;

public class LocationStore extends AbstractStore<Location> {

	public LocationStore() {
		super();
	}

	public LocationStore(boolean inMemory) {
		super(inMemory);
	}

	@Override
	protected Class<Location> getStoreClass() {
		return Location.class;
	}
	
}
