package org.inm.notification;

import org.inm.store.AbstractStore;

public class NotificationStore extends AbstractStore<Notification> {

	public NotificationStore(boolean inMemory) {
		super(inMemory);
	}

	@Override
	protected Class<Notification> getStoreClass() {
		return Notification.class;
	}

	@Override
	public Notification insert(Notification entity) {
		if (entity.getId() == null) {
			entity.createId();
		}
		return super.insert(entity);
	}

	@Override
	public boolean exists(Notification entity) {
		if (entity.getId() == null) {
			entity.createId();
		}
		return super.exists(entity);
	}
	
	

}
