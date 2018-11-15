package org.inm.store.search;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Cursor;
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
	    
		return findByIdField(entity.getClass(), idValue) != null;
    }
    
    public T findByIdField (Class<?> entityClass, Object idValue) {
	    
	    Field idField = inspector.getIDField(entityClass);
	    Cursor<T> found = find(idField, idValue);
	    
	    return found.firstOrDefault();
    }
	
	Cursor<T> find (Field field, Object idValue) {
	    
	    String idFieldName = field.getName();
		return (Cursor<T>)find(idFieldName, idValue);
	}

	public Iterable<T> find(String idFieldName, Object idValue) {
		ObjectFilter filter = ObjectFilters.eq(idFieldName, idValue);
		return find(filter);
	}
	
	public Iterable<T> find(ObjectFilter filter) {
	    return this.repository.find(filter);
	}

	public Iterable<T> find(ObjectFilter filter, FindOptions findOptions) {
	    return this.repository.find(filter, findOptions);
	}
	
	public T find(NitriteId id) {
		return this.repository.getById(id);
	}

	public Iterable<T> findAll() {
		return this.repository.find();
	}

}
