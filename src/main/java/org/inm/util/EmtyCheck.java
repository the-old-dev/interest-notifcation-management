package org.inm.util;

public abstract class EmtyCheck {
	
	public static boolean isEmpty(String string) {
		
		if (string == null) {
			return true;
		}
		
		if ("".equals(string)) {
			return true;
		}
		
		if ("".equals(string.trim())) {
			return true;
		}
		
		return false;
	}

    public static  boolean isNullOrZero(Double value) {

		if (value == null) {
			return true;
		}

		return value.equals(0.0);

	}
}
