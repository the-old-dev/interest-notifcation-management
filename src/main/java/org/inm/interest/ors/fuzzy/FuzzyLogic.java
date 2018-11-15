package org.inm.interest.ors.fuzzy;

import java.util.ArrayList;
import java.util.List;

public abstract class FuzzyLogic {

	public static List<SearchPhrase> createFuzzySearchPhrases(String searchPhrase) {

		// Initialize
		List<SearchPhrase> fuzzies = new ArrayList<SearchPhrase>();

		// parse
		List<Token> tokens = Parser.parse(searchPhrase);

		// render
		fuzzies.add(Renderers.renderWithStrictDelimiting(tokens));

		// return
		return fuzzies;

	}

}
