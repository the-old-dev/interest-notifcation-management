package org.inm.notification;

import org.inm.store.AbstractStore;
import org.inm.subscription.Subscription;

public class NotificationStore extends AbstractStore<Notification> {

    public NotificationStore(boolean inMemory) {
        super(inMemory);
    }

    public Notification findByPrimaryKeys(String aSubscriberEmail, Subscription aSubscription) {
        int id = Notification.createID(aSubscriberEmail, aSubscription);
        return getSearch(null).findByIdField(getStoreClass(), id);
    }

    @Override
    protected Class<Notification> getStoreClass() {
        return Notification.class;
    }

    /**
     * Overwrite this method to hook into write, before it happens.
     */
    @Override
    protected void beforeGetWrite(Notification entity) {
        enshureId(entity);
    }

    /**
     * Overwrite this method to hook into search, before it happens.
     */
    @Override
    protected void beforeGetSearch(Notification entity) {
        
        if (entity == null) {
            return;
        }
        
        enshureId(entity);
    }

    private void enshureId(Notification notification) {
        notification.enshureId();
    }

}
