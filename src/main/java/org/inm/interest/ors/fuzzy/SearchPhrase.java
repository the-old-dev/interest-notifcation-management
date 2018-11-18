package org.inm.interest.ors.fuzzy;

public class SearchPhrase {

	private StringBuffer phraseBuffer;
	private String countryCode;

	SearchPhrase() {
		phraseBuffer = new StringBuffer();
		countryCode = "DE";
	}

	public SearchPhrase(String phrase) {
		this();
		this.appendToPhrase(phrase);
	}

	SearchPhrase(String phrase, String countryCode) {
		this(phrase);
		this.setCountryCode(countryCode);
	}

	public String getPhrase() {

		return phraseBuffer.toString().trim();
	}

	void appendToPhrase(String phrase) {

		this.phraseBuffer.append(phrase);
	}

	public String getCountryCode() {

		return countryCode;
	}

	void setCountryCode(String countryCode) {

		this.countryCode = countryCode;
	}

}
