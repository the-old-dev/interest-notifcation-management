package org.inm.interest.ors.fuzzy;

public class DelimiterToken extends SpecialWordToken {

	static Token[] All = new Token[] { new DelimiterToken(" ", "-", "/", "|") };

	public DelimiterToken(String... fuzzies) {
		super(fuzzies);
	}

}
