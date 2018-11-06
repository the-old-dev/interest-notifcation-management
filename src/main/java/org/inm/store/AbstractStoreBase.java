
package org.inm.store;

import java.io.Serializable;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.inm.store.search.Search;

public abstract class AbstractStoreBase<T extends Serializable> {

	private Nitrite nitrite;
	private Write<T> write;
	private Search<T> search;

	public AbstractStoreBase(boolean inMemory) {
		Configuration configuration = new Configuration(this);
		this.nitrite = configuration.initialize(inMemory);
		ObjectRepository<T> repository = nitrite.getRepository(getStoreClass());
		this.write = new Write<T>(repository);
		this.search = new Search<T>(repository);
	}

	public void close() {
		this.nitrite.close();
	}

	public void register(AbstractChangeListener<T> listener) {
		listener.initialize(this.nitrite.getContext().getNitriteMapper(), getStoreClass());
		this.write.register(listener);
	}

	protected final Write<T> getWrite(T entity) {
		beforeGetWrite(entity);
		return write;
	}

	protected Search<T> getSearch(T entity) {
		beforeGetSearch(entity);
		return this.search;
	}

	/**
	 * Overwrite this method to hook into write, before it happens.
	 */
	protected void beforeGetWrite(T entity) {

	}

	/**
	 * Overwrite this method to hook into search, before it happens.
	 */
	protected void beforeGetSearch(T entity) {
		this.nitrite.commit();
	}

	abstract protected Class<T> getStoreClass();

}
