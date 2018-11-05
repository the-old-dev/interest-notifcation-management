package org.inm.interest;

import org.inm.interest.Interest;
import org.inm.interest.InterestStore;
import org.inm.store.AbstractStore;
import org.inm.store.AbstractStoreTestCase;

import org.junit.Test;

import com.cedarsoftware.util.DeepEquals;

public class InterestStoreTestCase extends AbstractStoreTestCase<Interest> {

	@Override
	protected AbstractStore<Interest> createStore() throws Exception {
		return new InterestStore(true);
	}

	@Override
	protected Class<Interest> getEntityClass() {
		return Interest.class;
	}

	@Test
	public void testWithLocation() throws Exception {
		
		Interest interest = createEntity();
		interest.getDetails().put("location", new Location("Sin City", 4.2, 42.0));
		
		this.store.insert(interest);
		
		Interest found = this.store.findByField("url", interest.getUrl()).iterator().next();
		
		DeepEquals.deepEquals(interest, found);
	}

}
