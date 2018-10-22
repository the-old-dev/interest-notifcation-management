package org.inm.store;

import java.io.Serializable;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.ObjectRepository;
import org.inm.util.NullCheck;

class Write<T extends Serializable> {
	
	private ObjectRepository<T> repository;

	Write(ObjectRepository<T> repository) {
		this.repository = repository;
	}

	@SuppressWarnings("unchecked")
	NitriteId insert(T entity) {
	
		NullCheck.NotNull("entity", entity);
		return this.repository.insert(entity).iterator().next();

	}

	NitriteId update(T entity) {
	
		NullCheck.NotNull("entity", entity);
		return this.repository.update(entity).iterator().next();
	
	}

	public void delete(T entity) {
		this.repository.remove(entity);
	}

}
