package org.inm.interest.ors.fuzzy;

public class SeparatorToken extends SpecialWordToken {

	static TokenList All = new TokenList(new SeparatorToken("|", "/", "od.", "oder"));

	public SeparatorToken(String... fuzzies) {
		super(fuzzies);
	}

	@Override
	protected boolean testAcceptance(String phrase, String fuzzy) {
		return super.testStartsWith(phrase, fuzzy);
	}

}
