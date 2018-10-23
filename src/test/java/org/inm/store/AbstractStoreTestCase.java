package org.inm.store;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractStoreTestCase<T extends Serializable> {

	public AbstractStoreTestCase() {
		super();
	}

	protected abstract AbstractStore<T> createStore() throws Exception ;

	protected abstract T createEntity() throws Exception ;

	protected abstract void testEntity(T entity, T found) throws Exception ;
	
	protected abstract void updateEntity(T entity) throws Exception  ;

	@Test
	public void testCRUD() throws Exception {
		
		AbstractStore<T> store = createStore();
		
		T entity = createEntity();
		
		// Create
		entity = store.insert(entity);
		Assert.assertTrue(store.exists(entity));
		
		// Read
		Iterable<T> founds = store.findAll();
		int i = 0;
		for(T found : founds) {
			i = i +1;
			testEntity(entity, found);
		}
		Assert.assertEquals(1,i);
		
		// Update
		updateEntity(entity);
		store.update(entity);
		founds = store.findAll();
		i = 0;
		for(T found : founds) {
			i = i +1;
			testEntity(entity, found);
		}
		Assert.assertEquals(1,i);		
		
		// Delete
		store.delete(entity);
		Assert.assertFalse(store.exists(entity));
		Assert.assertFalse(store.findAll().iterator().hasNext());
	}



}