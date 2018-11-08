package org.inm.interest;

import java.util.Iterator;
import java.util.List;

import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.dizitart.no2.util.Iterables;
import org.inm.store.AbstractStore;
import org.springframework.stereotype.Component;

@Component
public class LocationStore extends AbstractStore<Location> {

	public LocationStore() {
		super();
	}

	public LocationStore(boolean inMemory) {
		super(inMemory);
	}

	@Override
	protected Class<Location> getStoreClass() {
		return Location.class;
	}

	public Location findByIdField(String name) {
		Iterator<Location> iterator = findByField("name", name).iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		} else {
			return null;
		}
	}

	public List<Location> findUnlocated() {
		return Iterables.toList(this.getSearch(null).find(createUnlocatedFilter()));
	}
	
	private ObjectFilter createUnlocatedFilter() {
	    
	    ObjectFilter filter1 
	      = ObjectFilters.or(ObjectFilters.eq("latitude", null), ObjectFilters.eq("longitude", null));
		
		ObjectFilter filter2 
		  = ObjectFilters.or(ObjectFilters.eq("latitude", 0.0), ObjectFilters.eq("longitude", 0.0));

        return ObjectFilters.or(filter1, filter2);
	}

}
