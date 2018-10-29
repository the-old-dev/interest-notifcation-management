package org.inm.interest;

import org.inm.interest.Interest;
import org.inm.interest.InterestStore;
import org.inm.store.AbstractStore;
import org.inm.store.AbstractStoreTestCase;

import org.junit.Test;

public class InterestStoreTestcase extends AbstractStoreTestCase<Interest> {

	@Override
	protected AbstractStore<Interest> createStore() throws Exception {
		return new InterestStore(true);
	}

	@Override
	protected Class<Interest> getEntityClass() {
		return Interest.class;
	}
	
	@Test
	public void dummy() {}

}
