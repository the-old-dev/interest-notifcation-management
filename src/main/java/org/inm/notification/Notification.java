package org.inm.notification;

import java.io.Serializable;
import java.util.List;

import org.dizitart.no2.objects.Id;
import org.inm.interests.Interest;
import org.inm.subscription.Subscription;

public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long timestamp;

	private String subscriberEmail;
	private Subscription subscription;
	private List<Interest> interests;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSubscriberEmail() {
		return subscriberEmail;
	}

	public void setSubscriberEmail(String subscriberEmail) {
		this.subscriberEmail = subscriberEmail;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public List<Interest> getInterests() {
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

}
