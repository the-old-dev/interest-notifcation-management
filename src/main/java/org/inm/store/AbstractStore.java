package org.inm.store;

import java.io.Serializable;
import java.util.List;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.util.Iterables;

public abstract class AbstractStore<T extends Serializable> extends AbstractStoreBase<T> {


	public AbstractStore(boolean inMemory) {
		super(inMemory);
	}

	// ### API ###
	
	public T insert (T entity) {
		
		if (getSearch(entity).exists(entity)) {
			throw new IllegalArgumentException("This entity exists allready in the Store, entity:=" + entity.toString());
		}

		NitriteId id = getWrite(entity).insert(entity);
		return getSearch(entity).find(id);
	}
	
	public boolean exists(T entity) {
		return this.getSearch(entity).exists(entity);
	}

	public void update(T entity) {
		
		if (!getSearch(entity).exists(entity)) {
			throw new IllegalArgumentException("Update not possible, this entity does not exist in the Store" + entity.toString());
		}
		
		this.getWrite(entity).update(entity);
	}
	
	public void delete(T entity) {
		
		if (!getSearch(entity).exists(entity)) {
			throw new IllegalArgumentException("Delete not possible, this entity does not exist in the Store" + entity.toString());
		}
		
		this.getWrite(entity).delete(entity);
	}

    public Iterable<T> findAll() {
		return this.getSearch((T)null).findAll();
	}
    
    public List<T> findAllAsList() {
    	return Iterables.toList(this.findAll());
    }
    
    public List<T> findByField(String fieldName, Object value) {
		return Iterables.toList(getSearch(null).find(fieldName, value));
	}
	
}