package org.inm.notification;

import java.io.Serializable;
import java.util.List;

import org.dizitart.no2.objects.Id;
import org.inm.interest.Interest;
import org.inm.subscription.Subscription;

public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String subscriberEmail;
	private Subscription subscription;
	private List<Interest> interests;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	void generateId() {
		setId(createId());
	}

	int createId() {
		String aSubscriberEmail = subscriberEmail;
		Subscription aSubscription = subscription;
		return createID(aSubscriberEmail, aSubscription);
	}

	public static int createID(String aSubscriberEmail, Subscription aSubscription) {
		String idBase = aSubscriberEmail + aSubscription.getName() + aSubscription.getWebsiteUrl().toString()
				+ aSubscription.getSubscribableUrl();
		for (String rule : aSubscription.getRules()) {
			idBase = idBase + rule;
		}
		return idBase.hashCode();
	}

}
