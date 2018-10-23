package org.inm.website;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.inm.store.AbstractStore;
import org.inm.store.AbstractStoreTestCase;
import org.junit.Assert;

public class WebsiteStoreTestcase extends AbstractStoreTestCase<Website> {

	@Override
	protected AbstractStore<Website> createStore() throws Exception {
		return new WebsiteStore(true);
	}

	@Override
	protected Website createEntity() throws Exception {

		Website website = new Website();
		List<Subscribable> subscribables = new ArrayList<>();
		Subscribable subscribable = new Subscribable();

		subscribables.add(subscribable);
		website.setSubscribables(subscribables);

		website.setUrl("http://www.example.org");
		subscribable.setName("Things");
		subscribable.setUrl(new URL("http://www.example.org/abc"));

		return website;
	}

	@Override
	protected void testEntity(Website entity, Website found) throws Exception {
		Assert.assertEquals(entity.getUrl(), found.getUrl());
		List<Subscribable> foundSubscribables = found.getSubscribables();
		List<Subscribable> entitySubscribables = entity.getSubscribables();
		Assert.assertEquals(entitySubscribables.size(), foundSubscribables.size());
		Assert.assertEquals(entitySubscribables.iterator().next().getName(), foundSubscribables.iterator().next().getName());
		Assert.assertEquals(entitySubscribables.iterator().next().getUrl(), foundSubscribables.iterator().next().getUrl());
	}

	@Override
	protected void updateEntity(Website entity) throws Exception {
		// Entity-URL can not be updated, it's the id
		entity.getSubscribables().iterator().next().setName("Things Updated");
		entity.getSubscribables().iterator().next().setUrl(new URL("http://updated.org/xyz"));
	}

}
