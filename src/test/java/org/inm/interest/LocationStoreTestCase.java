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
	
	@Test
	public void testFindUnlocated() throws Exception {
	    
	   // Prepare test data in store 
	   this.store.insert(new Location("a", 10.0, 20.0));
	   this.store.insert(new Location("b", 0.0, 0.0));   
	   this.store.insert(new Location("c", 0.00, 0.00));   
	   this.store.insert(new Location("d", 0.000, 0.000));   
	   this.store.insert(new Location("e", 20.0, 20.0));
	   
	   // execute search
	   List<Location> found = ((LocationStore)this.store).findUnlocated();
	   
	   // test found elements
	   Assert.assertNotNull(found);
	   Assert.assertEquals(3, found.size());
	   
    }
}
