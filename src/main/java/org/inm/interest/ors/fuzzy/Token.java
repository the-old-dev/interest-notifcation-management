package org.inm.interest.ors.fuzzy;

import java.util.ArrayList;
import java.util.List;

abstract class Token {

	static class TokenList extends ArrayList<Token> {

		private static final long serialVersionUID = 1L;

		TokenList(Token... tokens) {
			for (Token token : tokens) {
				this.add(token);
			}
		}

		String accept(String phrase, List<Token> newTokens) {

			for (Token myToken : this) {
				String accepted = myToken.accept(phrase, newTokens);
				if (accepted != null) {
					return accepted;
				}
			}
			return null;
		}

	}

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

	/**
	 * Tests of full equality of phrase and fuzzy. The method {@link #isCaseRelevant()} is used.
	 * 
	 * @param phrase
	 * @param fuzzy
	 * @return
	 */
	protected boolean testEquality(String phrase, String fuzzy) {

		if (isCaseRelevant()) {
			return phrase.equals(fuzzy);
		} else {
			return phrase.toLowerCase().equals(fuzzy.toLowerCase());
		}
	}

	protected boolean testStartsWith(String phrase, String fuzzy) {

		if (isCaseRelevant()) {
			return phrase.startsWith(fuzzy);
		} else {
			return phrase.toLowerCase().startsWith(fuzzy.toLowerCase());
		}
	}

	protected boolean testAcceptance(String phrase, String fuzzy) {

		return testStartsWith(phrase, fuzzy);
	}

	protected abstract Token newToken();

	protected abstract String[] getfuzzies();

}
