package org.inm.interest;

import java.util.List;
import java.util.Map;

class Navigateable {

	private Object collection;

	Navigateable(Object collection) {
		this.collection = collection;
	}

	@SuppressWarnings("unchecked")
	Navigateable navigate(String key) {

		Object value = null;

		if (collection instanceof Map) {
			value = ((Map<String, ?>) collection).get(key);
		} else if (collection instanceof List) {
			value = ((List<?>) collection).get(Integer.parseInt(key));
		}

		if (value != null) {
			return new Navigateable(value);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	 <T> T getCollection() {
		return (T) collection;
	}

}
