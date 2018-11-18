package org.inm.interest.ors.fuzzy;

public abstract class SpecialWordToken extends Token {

	private String[] fuzzies;

	SpecialWordToken(String... fuzzies) {
		this.fuzzies = fuzzies;
	}

	@Override
	protected Token newToken() {

		return this;
	}

	@Override
	protected String[] getfuzzies() {

		return fuzzies;
	}

	protected boolean testAcceptance(String phrase, String fuzzy) {

		return testEquality(phrase, fuzzy);
	}

}
