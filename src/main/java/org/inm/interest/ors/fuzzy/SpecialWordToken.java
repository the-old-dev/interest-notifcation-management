package org.inm.interest.ors.fuzzy;

public class SpecialWordToken extends Token {

	static Token[] All = new Token[]{
		new SpecialWordToken("St.", "St","Sankt"),
		new SpecialWordToken("a.","am"),
		new SpecialWordToken("i.","im"),
		new SpecialWordToken("City"),
	};
	
	private String[] fuzzies;

	SpecialWordToken(String ... fuzzies) {
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

}
