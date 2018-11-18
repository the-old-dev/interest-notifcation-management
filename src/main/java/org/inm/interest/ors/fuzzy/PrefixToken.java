package org.inm.interest.ors.fuzzy;

public class PrefixToken extends SpecialWordToken {

	static TokenList All = new TokenList(new PrefixToken("St.", "St", "Sankt"));

	public PrefixToken(String... fuzzies) {
		super(fuzzies);
	}

	protected boolean testAcceptance(String phrase, String fuzzy) {
		return testStartsWith(phrase, fuzzy);
	}

}
