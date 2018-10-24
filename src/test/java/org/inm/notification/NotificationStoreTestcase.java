package org.inm.notification;

import org.inm.store.AbstractStore;
import org.inm.store.AbstractStoreTestCase;
import org.junit.Assert;
import org.junit.Test;

public class NotificationStoreTestcase extends AbstractStoreTestCase<Notification> {

	@Override
	protected AbstractStore<Notification> createStore() throws Exception {
		return new NotificationStore(true);
	}

	@Override
	protected Class<Notification> getEntityClass() {
		return Notification.class;
	}
	
	@Test
	public void testIdGeneration() throws Exception {
		Notification one = createEntity();
		Notification other = createEntity();
		
		Integer initialId = one.getId();
		one.generateId();
		Assert.assertNotEquals(initialId, one.getId());	
		
		other.generateId();
		Assert.assertEquals(one.getId(), other.getId());
	}
}
