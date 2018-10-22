package org.inm.store;

import org.junit.Assert;
import org.junit.Test;

public class StoreTestCase {
	
	@Test
	public void testCRUD() throws Exception {
		
		TestStore store = new TestStore(true);
		
		TestEntity entity = new TestEntity();
		entity.setUrl("http://www.example.org/abc");
		entity.setName("MyName A");
		
		// Create
		entity = store.insert(entity);
		Assert.assertTrue(store.exists(entity));
		
		// Read
		Iterable<TestEntity> founds = store.findAll();
		int i = 0;
		for(TestEntity found : founds) {
			i = i +1;
			Assert.assertEquals(entity.getUrl(), found.getUrl());
			Assert.assertEquals(entity.getName(), found.getName());
		}
		Assert.assertEquals(1,i);
		
		// Update
		entity.setName("MyName B");
		store.update(entity);
		founds = store.findAll();
		i = 0;
		for(TestEntity found : founds) {
			i = i +1;
			Assert.assertEquals(entity.getUrl(), found.getUrl());
			Assert.assertEquals(entity.getName(), found.getName());
		}
		Assert.assertEquals(1,i);		
		
		// Delete
		store.delete(entity);
		Assert.assertFalse(store.exists(entity));
		Assert.assertFalse(store.findAll().iterator().hasNext());
	}

}
