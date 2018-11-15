package org.inm.interest.ors.fuzzy;

public class CountryToken extends SpecialWordToken {

	static Token[] All = new Token[] { new CountryToken("BE", "BEL", "Belgien"),
			new CountryToken("DE", "DEU", "Deutschland"), new CountryToken("NL", "NLD", "Niederlande", "Holland"),
			new CountryToken("AT", "AUT", "Austria", "Österreich"), new CountryToken("IT", "Italien"),
			new CountryToken("CH", "CHE", "Schweiz"), new CountryToken("FR", "FRA", "Frankreich"),
			new CountryToken("SE", "SWE", "Schweden"), new CountryToken("IT", "ITA", "Italien") };

	CountryToken(String... fuzzies) {
		super(fuzzies);
	}
	
	protected boolean testAcceptance(String phrase, String fuzzy) {
		if (isCaseRelevant()) {
			return phrase.equals(fuzzy);
		} else {
			return phrase.toLowerCase().equals(fuzzy.toLowerCase());
		}
	}

}
