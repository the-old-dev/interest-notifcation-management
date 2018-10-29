package org.inm.server.service;

import java.util.List;

import org.inm.subscription.Subscriber;
import org.inm.subscription.SubscriberStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionEndpoint {

	@Autowired
	SubscriberStore subscriberStore;

	@RequestMapping("/api/subscriber")
	public List<Subscriber> subscriber() {
		return subscriberStore.findAllAsList();
	}

	@RequestMapping(value = "/api/subscriber", method = RequestMethod.POST)
	public void subscriber(Subscriber subscriber) {
		if (this.subscriberStore.exists(subscriber)) {
			this.subscriberStore.update(subscriber);
		} else {
			this.subscriberStore.insert(subscriber);
		}
	}

}
