package org.inm.interest.ors.fuzzy;

import java.util.List;

public class Renderer {

	SearchPhrase render(List<Token> tokenList) {
		SearchPhrase searchPhrase = new SearchPhrase();
		render(tokenList, searchPhrase);
		return searchPhrase;
	}

	private void render(List<Token> tokenListn, SearchPhrase searchPhrase) {
		for (Token token : tokenListn) {
			render(token, searchPhrase);
		}
	}

	private void render(Token token, SearchPhrase searchPhrase) {

		// Null check
		if (token == null) {
			return;
		}

		// handle country
		if (token.getClass().equals(CountryToken.class)) {
			searchPhrase.setCountryCode(token.getfuzzies()[0]);
			return;
		}

		// render the first fuzzie
		searchPhrase.appendToPhrase(token.getfuzzies()[0]);
	}

}
