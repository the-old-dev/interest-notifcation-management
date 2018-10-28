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

}
