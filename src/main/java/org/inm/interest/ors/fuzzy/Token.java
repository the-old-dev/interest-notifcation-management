package org.inm.interest.ors.fuzzy;

import java.util.List;

abstract class Token {

	String accept(String phrase, List<Token> tokens) {

		if (phrase == null || "".equals(phrase)) {
			return null;
		}

		for (String fuzzy : getfuzzies()) {
			if (testAcceptance(phrase, fuzzy)) {
				tokens.add(newToken());
				return phrase.substring(fuzzy.length());
			}
		}

		return null;
	}

	protected boolean isCaseRelevant() {
		return false;
	}

	protected boolean testAcceptance(String phrase, String fuzzy) {
		if (isCaseRelevant()) {
			return phrase.startsWith(fuzzy);
		} else {
			return phrase.toLowerCase().startsWith(fuzzy.toLowerCase());
		}
	}

	protected abstract Token newToken();

	protected abstract String[] getfuzzies();

}
