package org.inm.interest;

import org.inm.store.AbstractStore;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;

@Component
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
