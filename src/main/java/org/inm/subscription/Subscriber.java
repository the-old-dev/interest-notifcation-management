package org.inm.subscription;

import java.io.Serializable;
import java.util.List;

import org.dizitart.no2.objects.Id;

public class Subscriber implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String email;

	private List<Subscription> subscriptions;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

}
