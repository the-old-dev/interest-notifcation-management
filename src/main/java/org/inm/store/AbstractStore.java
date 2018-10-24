package org.inm.store;

import java.io.Serializable;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.ObjectRepository;
import org.inm.store.search.Search;

public abstract class AbstractStore<T extends Serializable> {

	protected Nitrite nitrite;
	protected Write<T> write;
	protected Search<T> search;

	public AbstractStore(boolean inMemory) {
		
		Configuration configuration = new Configuration(this);
		this.nitrite = configuration.initialize(inMemory);
		
		ObjectRepository<T> repository = nitrite.getRepository(getStoreClass());
		this.write = new Write<T>(repository);
		this.search = new Search<T>(repository);
	}

	public void close() {
		this.nitrite.close();
	}
	
	abstract protected Class<T> getStoreClass();
	
	// ### API ###
	
	public T insert (T entity) {
		
		if (search.exists(entity)) {
			throw new IllegalArgumentException("This entity exists allready in the Store" + entity.toString());
		}

		NitriteId id = write.insert(entity);
		return search.find(id);
	}
	
	public boolean exists(T entity) {
		return this.search.exists(entity);
	}

	public void update(T entity) {
		
		if (!search.exists(entity)) {
			throw new IllegalArgumentException("Update not possible, this entity does not exist in the Store" + entity.toString());
		}
		
		this.write.update(entity);
	}
	
	public void delete(T entity) {
		
		if (!search.exists(entity)) {
			throw new IllegalArgumentException("Delete not possible, this entity does not exist in the Store" + entity.toString());
		}
		
		this.write.delete(entity);
	}

	public Iterable<T> findAll() {
		return this.search.findAll();
	}
	
	public void register(AbstractChangeListener<T> listener) {
		listener.initialize(this.nitrite.getContext().getNitriteMapper(), getStoreClass());
		this.write.register(listener);
	}
}