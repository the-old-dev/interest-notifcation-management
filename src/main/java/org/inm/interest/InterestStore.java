package org.inm.interest;

import java.util.List;

import org.dizitart.no2.util.Iterables;
import org.inm.store.AbstractStore;

public class InterestStore extends AbstractStore<Interest> {

	public InterestStore() {
		this(false);
	}

	public InterestStore(boolean inMemory) {
		super(inMemory);
	}

	public List<Interest> findByField(String fieldName, Object value) {
		return Iterables.toList(getSearch(null).find(fieldName, value));
	}

	@Override
	protected Class<Interest> getStoreClass() {
		return Interest.class;
	}

}
