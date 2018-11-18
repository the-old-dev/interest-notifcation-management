package org.inm.interest.ors.fuzzy;

import java.util.List;

class StrictlyDelimitingRenderStrategy extends RenderStrategy {



	@Override
	void runStrategy(List<Token> tokenList) {
		
		// The last element is not considered
		int n = tokenList.size() - 1;

		// nothing todo for only one elment
		if (n < 1) {
			return;
		}

		// do now
		for (int i = 0; i < (n); i++) {

			Token current = tokenList.get(i);
			Token next = tokenList.get(i + 1);

			boolean needsDelimiter = !(current instanceof DelimiterToken);
			boolean hasDelimiter = (next instanceof DelimiterToken);

			if (needsDelimiter && !hasDelimiter) {

				// insert delimiter
				tokenList.add(i + 1, DelimiterToken.All.get(0));

				// enlarge the collection counting
				n = n + 1;

			}

		}
		
	}

}
