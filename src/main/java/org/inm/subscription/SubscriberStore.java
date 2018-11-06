package org.inm.subscription;

import org.inm.store.AbstractStore;
import org.springframework.stereotype.Component;

@Component
public class SubscriberStore extends AbstractStore<Subscriber> {

	public SubscriberStore() {
		this(false);
	}
	
	public SubscriberStore(boolean inMemory) {
		super(inMemory);
	}

	@Override
	protected Class<Subscriber> getStoreClass() {
		return Subscriber.class;
	}

}
