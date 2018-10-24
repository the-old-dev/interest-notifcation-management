package org.inm.store;

public class TestStoreTestCase extends AbstractStoreTestCase<TestEntity> {

	@Override
	protected AbstractStore<TestEntity> createStore() throws Exception {
		return new TestStore(true);
	}

	@Override
	protected Class<TestEntity> getEntityClass() {
		return TestEntity.class;
	}

}
