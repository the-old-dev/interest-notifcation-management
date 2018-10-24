package org.inm.subscription;

import org.inm.store.AbstractStore;
import org.inm.store.AbstractStoreTestCase;

public class SubscriberStoreTestcase extends AbstractStoreTestCase<Subscriber> {

	@Override
	protected Class<Subscriber> getEntityClass() {
		return Subscriber.class;
	}

	@Override
	protected AbstractStore<Subscriber> createStore() throws Exception {
		return new SubscriberStore(true);
	}

}
