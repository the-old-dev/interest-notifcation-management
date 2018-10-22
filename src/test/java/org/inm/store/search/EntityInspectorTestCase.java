package org.inm.store.search;

import java.lang.reflect.Field;

import org.inm.store.TestEntity;
import org.junit.Assert;
import org.junit.Test;

public class EntityInspectorTestCase {

	@Test
	public void testIdInspection() throws Exception {

		String url = "https://www.example.com/abc";

		TestEntity entity = new TestEntity();
		entity.setUrl(url);

		EntityInspector inspector = new EntityInspector();
		Field idField = inspector.getIDField(TestEntity.class);
		Assert.assertNotNull(idField);
		Assert.assertEquals("url", idField.getName());

		Object idFieldValue = inspector.getValue(entity, idField);
		Assert.assertEquals(url, idFieldValue);

	}

}