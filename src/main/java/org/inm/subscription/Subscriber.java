package org.inm.subscription;

import java.io.Serializable;
import java.util.List;

import org.dizitart.no2.objects.Id;

public class Subscriber implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String eMail;

	private long timestamp;

	private List<Subscription> subscriptions;

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

}
