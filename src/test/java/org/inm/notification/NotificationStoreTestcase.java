package org.inm.notification;

import java.net.URL;

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
		one.enshureId();
		Assert.assertNotEquals(initialId, one.getId());	
		
		other.enshureId();
		Assert.assertEquals(one.getId(), other.getId());
	}
	
	@Test
	public void testFind() throws Exception {
		
		NotificationStore nStore = (NotificationStore) this.store;
	    
	    Notification one = createEntity();
	    Notification two = createEntity();
	    
	    two.getSubscription().setSubscribableUrl(new URL(two.getSubscription().getSubscribableUrl(), "/001"));
	    
	    nStore.insert(one);
	    nStore.insert(two);
	    
	    Notification foundOne = nStore.findByPrimaryKeys(one.getSubscriberEmail(), one.getSubscription());
	    this.testEntity(one, foundOne);
	    
	    Notification foundTwo = nStore.findByPrimaryKeys(two.getSubscriberEmail(), two.getSubscription());
	    this.testEntity(two, foundTwo);
	    
	}
	
}
