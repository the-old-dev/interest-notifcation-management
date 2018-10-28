package org.inm.store;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cedarsoftware.util.DeepEquals;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractStoreTestCase<T extends Serializable> {

	private static final String PATH_RESOURCES_ENTITY = "src/test/resources/entity/";
	
	protected AbstractStore<T> store;

	public AbstractStoreTestCase() {
		super();
	}

	protected abstract AbstractStore<T> createStore() throws Exception ;

	protected abstract Class<T> getEntityClass();

    @Before
    public void setup() throws Exception {
    	store = createStore();
    }
    
    @After
    public void teardown() throws Exception {
    	store.close();
    }
    

	@Test
	public void testCRUD() throws Exception {
		
		T entity = createEntity();
		
		// Create
		entity = store.insert(entity);
		Assert.assertTrue(store.exists(entity));
		
		// Read
		Iterable<T> founds = store.findAll();
		int i = 0;
		for(T found : founds) {
			i = i +1;
			testEntity(entity, found);
		}
		Assert.assertEquals(1,i);
		
		// Update
		entity = createEntityUpdate();
		store.update(entity);
		founds = store.findAll();
		i = 0;
		for(T found : founds) {
			i = i +1;
			testEntity(entity, found);
		}
		Assert.assertEquals(1,i);		
		
		// Delete
		store.delete(entity);
		Assert.assertFalse(store.exists(entity));
		Assert.assertFalse(store.findAll().iterator().hasNext());
	}

	protected T createEntity() throws Exception {
		return createEntity(getEntityName());
	}

	protected T createEntityUpdate() throws Exception {
		return createEntity(getEntityName()+"Update");
	}
	
	private T createEntity(String entityName) throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(PATH_RESOURCES_ENTITY+entityName+".json");
		return objectMapper.readValue(file, getEntityClass());
	}

	private String getEntityName() {
		return getEntityClass().getSimpleName();
	}
	
	protected void testEntity(T entity, T found) throws Exception {
        Assert.assertTrue(DeepEquals.deepEquals(entity, found));
    }

}