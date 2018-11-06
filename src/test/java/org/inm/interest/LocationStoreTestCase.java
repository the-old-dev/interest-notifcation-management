package org.inm.interest;

import org.inm.store.AbstractStore;
import org.inm.store.AbstractStoreTestCase;
import org.junit.Test;

import org.junit.Assert;

import com.cedarsoftware.util.DeepEquals;

import java.util.List;

public class LocationStoreTestCase extends AbstractStoreTestCase<Location> {

	@Override
	protected AbstractStore<Location> createStore() throws Exception {
		return new LocationStore(true);
	}

	@Override
	protected Class<Location> getEntityClass() {
		return Location.class;
	}
	
	@Test
	public void testFindByIdField() throws Exception {
		
		Location location = createEntity();
		
		this.store.insert(location);
		List<Location> found = ((LocationStore)this.store).findByField("name", location.getName());
		
		Assert.assertNotNull(found);
		Assert.assertEquals(1, found.size());
		Assert.assertTrue(DeepEquals.deepEquals(location, found.get(0)));
		
	}

}
