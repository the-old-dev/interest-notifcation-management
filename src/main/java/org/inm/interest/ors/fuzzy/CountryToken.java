package org.inm.interest.ors.fuzzy;

public class CountryToken extends SpecialWordToken {

	public static final String FRANCE = "FR";
	public static final String ITALY = "IT";
	public static final String SWEDEN = "SE";
	public static final String SWISS = "CH";
	public static final String AUSTRIA = "AT";
	public static final String Netherlands = "NL";
	public static final String GERMANY = "DE";
	public static final String BELGIUM = "BE";

	static TokenList All = new TokenList(new CountryToken(BELGIUM, "BEL", "Belgien"),
			new CountryToken(GERMANY, "DEU", "Deutschland"),
			new CountryToken(Netherlands, "NLD", "Niederlande", "Holland"),
			new CountryToken(AUSTRIA, "AUT", "Austria", "Österreich"), 
			new CountryToken(ITALY, "Italien"),
			new CountryToken(SWISS, "CHE", "Schweiz"), 
			new CountryToken(FRANCE, "FRA", "Frankreich"),
			new CountryToken(SWEDEN, "SWE", "Schweden"), 
			new CountryToken(ITALY, "ITA", "Italien"));

	CountryToken(String... fuzzies) {
		super(fuzzies);
	}

}
