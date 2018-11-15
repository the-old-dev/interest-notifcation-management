package org.inm.interest.ors.fuzzy;

import java.util.List;

import org.inm.util.EmtyCheck;

public class CharacterToken extends Token {
	
	static CharacterToken TOKEN = new CharacterToken();

	private String value;

	private CharacterToken() {
		
	}
	
	private CharacterToken(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	String accept(String phrase, List<Token> tokens) {
		
		if (EmtyCheck.isEmpty(phrase)) {
			return null;
		}
		
		// simply take the first char
		tokens.add(new CharacterToken(phrase.substring(0, 1)));
		
		return phrase.substring(1);
		
	}

	@Override
	protected Token newToken() {
		return null;
	}

	@Override
	protected String[] getfuzzies() {
		return null;
	}

}
