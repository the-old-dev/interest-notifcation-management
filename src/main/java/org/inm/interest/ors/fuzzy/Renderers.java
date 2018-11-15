package org.inm.interest.ors.fuzzy;

import java.util.List;

abstract class Renderers {

	private static Object lock = new Object();
	private static StrictlyDelimitingRenderStrategy strategy = new StrictlyDelimitingRenderStrategy();
	private static Renderer renderer = new Renderer();

	static SearchPhrase renderWithStrictDelimiting(List<Token> tokenList) {
		synchronized (lock) {
			strategy.runStrategy(tokenList);
			return render(tokenList);
		}
	}

	private static SearchPhrase render(List<Token> tokenList) {
		return renderer.render(tokenList) ;
	}

}
