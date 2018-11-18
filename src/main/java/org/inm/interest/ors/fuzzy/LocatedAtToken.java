package org.inm.interest.ors.fuzzy;

public class LocatedAtToken extends SpecialWordToken {

	static TokenList All = new TokenList(new LocatedAtToken("a.", "am"), new LocatedAtToken("i.", "im"));

	public LocatedAtToken(String... fuzzies) {
		super(fuzzies);
	}

	protected boolean testAcceptance(String phrase, String fuzzy) {

		return testStartsWith(phrase, fuzzy);
	}

}
