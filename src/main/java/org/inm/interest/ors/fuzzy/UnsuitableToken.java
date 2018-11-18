package org.inm.interest.ors.fuzzy;

public class UnsuitableToken extends SpecialWordToken {

	static TokenList All = new TokenList(new UnsuitableToken("schicken", "Versand", "Sonstwo"));

	UnsuitableToken(String... fuzzies) {
		super(fuzzies);
	}

}
