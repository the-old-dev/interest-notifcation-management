package org.inm.subscription;

import org.inm.store.AbstractStore;

public class SubscriberStore extends AbstractStore<Subscriber> {

	public SubscriberStore(boolean inMemory) {
		super(inMemory);
	}

	@Override
	protected Class<Subscriber> getStoreClass() {
		return Subscriber.class;
	}

}
