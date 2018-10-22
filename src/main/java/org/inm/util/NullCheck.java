package org.inm.util;

public abstract class NullCheck {

	public static void NotNull(String name, Object value) {
		if (value == null) {
			throw new IllegalArgumentException(name + " must not be null!");
		}
	}

}
