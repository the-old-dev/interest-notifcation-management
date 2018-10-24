package org.inm.store;

public class StoreTestCase extends AbstractStoreTestCase<TestEntity> {

	@Override
	protected void updateEntity(TestEntity entity) throws Exception {
		entity.setName("MyName B");
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
		return new TestStore(true);
	}

}
