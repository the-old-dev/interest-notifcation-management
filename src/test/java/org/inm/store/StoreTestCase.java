package org.inm.store;

import org.junit.Assert;

public class StoreTestCase extends AbstractStoreTestCase<TestEntity> {

	@Override
	protected void updateEntity(TestEntity entity) throws Exception {
		entity.setName("MyName B");
	}

	@Override
	protected void testEntity(TestEntity entity, TestEntity found) throws Exception {
		Assert.assertEquals(entity.getUrl(), found.getUrl());
		Assert.assertEquals(entity.getName(), found.getName());
	}

	@Override
	protected TestEntity createEntity() throws Exception {
		TestEntity entity = new TestEntity();
		entity.setUrl("http://www.example.org/abc");
		entity.setName("MyName A");
		return entity;
	}

	@Override
	protected AbstractStore<TestEntity> createStore() throws Exception {
		TestStore store = new TestStore(true);
		return store;
	}

}
