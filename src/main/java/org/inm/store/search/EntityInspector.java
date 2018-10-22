package org.inm.store.search;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.dizitart.no2.objects.Id;

class EntityInspector {

	private Map<Class<?>, Method> idGetter;
	private Map<Class<?>, Field> idFields;

	EntityInspector() {
		this.idGetter = new HashMap<>();
		this.idFields = new HashMap<>();
	}

	Field getIDField(Class<? extends Serializable> entityClass) {

		Field idField = findIDField(entityClass);

		if (idField != null) {
			return idField;
		} else {
			throw new IllegalArgumentException("The class:=" + entityClass.getCanonicalName() + " has no Id field!");
		}
	}

	Object getValue(Object entity, Field field) {
		
		try {
			
			Method getter = getGetter(field);

			if (getter == null) {
				throw new IllegalArgumentException(
						"The field:=" + field.getName() + " has no getter in its class:=" + field.getDeclaringClass());
			}

			return getter.invoke(entity, (Object[]) null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private Method getGetter(Field idField) {

		Class<?> entityClass = idField.getDeclaringClass();

		Method getter = this.idGetter.get(entityClass);

		if (getter != null) {
			return getter;
		}

		try {
			getter = entityClass.getDeclaredMethod("get" + firstToUpperCase(idField.getName()));
			this.idGetter.put(entityClass, getter);
			return getter;
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	private String firstToUpperCase(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private Field findIDField(Class<? extends Serializable> entityClass) {

		Field idField = this.idFields.get(entityClass);
		
		if (idField != null) {
			return idField;
		}
		
		Field[] fields = entityClass.getDeclaredFields();

		for (Field field : fields) {
			if (isIdField(field)) {
				idField = field;
				this.idFields.put(entityClass, idField);
			}
		}

		return idField;
	}

	private boolean isIdField(Field field) {
		Annotation[] annotations = field.getDeclaredAnnotations();

		for (Annotation annotation : annotations) {
			if (annotation instanceof Id) {
				return true;
			}
		}

		return false;
	}

}
