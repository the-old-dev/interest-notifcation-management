package org.inm.interest;

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
	
	public List<Location> findUnlocated() {
	    
	    ObjectFilter filter = 
	       ObjectFilters.or(ObjectFilters.eq("latitude", 0.0), ObjectFilters.eq("longitude", 0.0));
	    
	    return Iterables.toList(this.getSearch(null).find(filter));
	}
	
}
