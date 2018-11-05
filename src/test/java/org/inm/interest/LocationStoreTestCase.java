package org.inm.interest;

import org.inm.store.AbstractStore;
import org.inm.store.AbstractStoreTestCase;
import org.junit.Test;
import com.cedarsoftware.util.DeepEquals;
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
		Location found = ((LocationStore)this.store).findByIdField(location.getName());
		
		DeepEquals.deepEquals(location, found);
		
	}

}
