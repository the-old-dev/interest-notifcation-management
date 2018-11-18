package org.inm.interest.ors.fuzzy;

import java.util.List;

public abstract class FuzzyLogic {

	public static List<SearchPhrase> createFuzzySearchPhrases(String searchPhrase) {

		// parse
		List<List<Token>> tokenChains = Parser.parse(searchPhrase);

		// render
		return Renderers.renderAllWithStrictDelimiting(tokenChains);

	}

}
