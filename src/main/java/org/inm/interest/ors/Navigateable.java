package org.inm.interest.ors;

import java.util.List;
import java.util.Map;

class Navigateable {

	private Object collection;

	Navigateable(Object collection) {
		this.collection = collection;
	}

    /**
     * Returns an object in any case.
     */
	@SuppressWarnings("unchecked")
	Navigateable navigate(String key) {

        if (this.collection == null) {
            return new Navigateable(null);
        }

		Object value = null;

		if (collection instanceof Map) {
			value = ((Map<String, ?>) collection).get(key);
		} else if (collection instanceof List) {
		    int index = Integer.parseInt(key);
		    if (index < ((List<?>) collection).size()) {
			 value = ((List<?>) collection).get(index);
		    }
		}

		return new Navigateable(value);

	}

	@SuppressWarnings("unchecked")
	 <T> T getCollection() {
		return (T) collection;
	}

}