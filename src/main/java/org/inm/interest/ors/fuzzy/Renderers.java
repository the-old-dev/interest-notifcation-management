package org.inm.interest.ors.fuzzy;

import java.util.ArrayList;
import java.util.List;

abstract class Renderers {

	private static Object lock = new Object();
	private static StrictlyDelimitingRenderStrategy strategy = new StrictlyDelimitingRenderStrategy();
	private static Renderer renderer = new Renderer();

	static List<SearchPhrase> renderAllWithStrictDelimiting(List<List<Token>> tokenChains) {

		synchronized (lock) {

			List<SearchPhrase> phrases = new ArrayList<SearchPhrase>();

			for (List<Token> tokenChain : tokenChains) {
				phrases.add(renderWithStrictDelimiting(tokenChain));
			}

			return phrases;
		}
	}

	static SearchPhrase renderWithStrictDelimiting(List<Token> tokenChain) {

		strategy.runStrategy(tokenChain);
		return renderer.render(tokenChain);
	}

}
