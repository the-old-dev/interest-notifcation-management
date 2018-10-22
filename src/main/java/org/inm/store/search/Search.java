package org.inm.store.search;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

public class Search<T extends Serializable> {

	private ObjectRepository<T> repository;
	private EntityInspector inspector;
	
	public Search(ObjectRepository<T> repository) {
		this.repository = repository;
		this.inspector = new EntityInspector();
	}

	public boolean exists(T entity) {
		
		Field idField = inspector.getIDField(entity.getClass());
		Object idValue = inspector.getValue(entity, idField);
		
		ObjectFilter filter = ObjectFilters.eq(idField.getName(), idValue);
		return this.repository.find(filter).iterator().hasNext();
		
	}
	
	public T find(NitriteId id) {
		return this.repository.getById(id);
	}

	public Iterable<T> findAll() {
		return this.repository.find();
	}

}
