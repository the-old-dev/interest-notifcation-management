package org.inm.interest.ors.fuzzy;

public class WordToken extends Token {

	private String[] value;

	public WordToken(CharacterToken charToken) {
		this.value = new String[] { charToken.getValue() };
	}

	void append(CharacterToken charToken) {
		this.value[0] =  this.value[0] + charToken.getValue();
	}

	@Override
	protected Token newToken() {
		return null;
	}

	@Override
	protected String[] getfuzzies() {
		return value;
	}

}
