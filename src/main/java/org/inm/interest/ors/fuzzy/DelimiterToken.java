package org.inm.interest.ors.fuzzy;

public class DelimiterToken extends SpecialWordToken {

	static TokenList All = new TokenList(new DelimiterToken(" ", "-") );

	public DelimiterToken(String... fuzzies) {
		super(fuzzies);
	}
	
	protected boolean testAcceptance(String phrase, String fuzzy) {
		return testStartsWith(phrase, fuzzy);
	}

}
