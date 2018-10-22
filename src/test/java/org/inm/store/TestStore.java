package org.inm.store;

public class TestStore extends AbstractStore<TestEntity> {

	public TestStore(boolean inMemory) {
		super(inMemory);
	}

	@Override
	protected Class<TestEntity> getStoreClass() {
		return TestEntity.class;
	}
	
}