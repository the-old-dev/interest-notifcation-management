package org.inm.store;

import java.io.Serializable;

import org.dizitart.no2.event.ChangeInfo;
import org.dizitart.no2.event.ChangeListener;
import org.dizitart.no2.event.ChangeType;
import org.dizitart.no2.event.ChangedItem;
import org.dizitart.no2.mapper.NitriteMapper;

public abstract class AbstractChangeListener<T extends Serializable> implements ChangeListener {

    private NitriteMapper mapper;
    private Class<T> entityClass;

	@Override
	public final void onChange(ChangeInfo changeInfo) {
		
		for (ChangedItem item : changeInfo.getChangedItems()) {
			
			T object = getObject(item);
			
			ChangeType changeType = item.getChangeType();
			
			switch (changeType) {
			case INSERT:
				onInsert(object);
				break;
		
			case REMOVE:
				onRemove(object);
				break;
			
			case UPDATE:
				onUpdate(object);
				break;
				
			default:
				break;
			}

		}

	}
	
	void initialize(NitriteMapper mapper, Class<T> entityClass) {
	    this.mapper = mapper;
	    this.entityClass = entityClass;
	}

	private  T getObject(ChangedItem item) {
		return this.mapper.asObject(item.getDocument(), entityClass);
	}
	
	protected  abstract  void onUpdate(T object) ;
	protected  abstract void onRemove(T object);
	protected  abstract void onInsert(T object);

}
