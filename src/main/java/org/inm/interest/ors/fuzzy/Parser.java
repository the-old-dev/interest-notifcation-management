package org.inm.interest.ors.fuzzy;

import java.util.ArrayList;
import java.util.List;

/**
 * The parsing is done in three phases:
 * 
 * 1) Create a token list with the elementary elements (SpecialWord, Delimiter, Character)
 * 
 * 2) Build a new list and replace the CharacterTokens Sequence with the WordToken tokens
 * 
 * 3) Build a new list and replace Words which are countries with CountryTokens
 * 
 * @author user
 *
 */
public class Parser {

	private static final String CHARACTER_TOKEN = "CharacterToken";
	private static final String WORD_TOKEN = "WordToken";

	static List<Token> parse(String phrase) {
		return parseThirdPhase(parseSecondPhase(parseFirstPhase(phrase)));
	}

	/**
	 * Build a new list and replace Words which are countries with CountryTokens.
	 * 
	 * @param parseSecondPhase
	 * @return
	 */
	private static List<Token> parseThirdPhase(List<Token> parseSecondPhase) {

		List<Token> thirdPhaseList = new ArrayList<Token>();

		for (Token token : parseSecondPhase) {

			switch (token.getClass().getSimpleName()) {

			case WORD_TOKEN:
				// add the token if it is not a CountryToken
				if (parseForTokens(token.getfuzzies()[0], thirdPhaseList, CountryToken.All) == null) {
					thirdPhaseList.add(token);
				}
				break;

			default:
				thirdPhaseList.add(token);
				break;

			}

		}

		return thirdPhaseList;

	}

	/**
	 * Build a new list and replace the CharacterTokens Sequence with the WordToken tokens.
	 * 
	 * @param phrase
	 * @return
	 */
	private static List<Token> parseSecondPhase(List<Token> firstPhaseList) {

		List<Token> secondPhaseList = new ArrayList<Token>();

		WordToken word = null;

		for (Token token : firstPhaseList) {

			switch (token.getClass().getSimpleName()) {

			case CHARACTER_TOKEN:
				if (word == null) {
					word = new WordToken((CharacterToken) token);
					secondPhaseList.add(word);
				} else {
					word.append((CharacterToken) token);
				}
				break;

			default:
				if (word != null) {
					word = null;
				}
				secondPhaseList.add(token);
				break;

			}

		}

		return secondPhaseList;
	}

	/**
	 * Create a token list with the elementary elements (SpecialWord, Delimiter, Character) in the following order:
	 * 
	 * - Special Words
	 * 
	 * - Delimiters
	 * 
	 * - Characters
	 * 
	 * @param phrase
	 * @return
	 */
	private static List<Token> parseFirstPhase(String phrase) {
		phrase = phrase.trim();

		List<Token> tokensList = new ArrayList<Token>();

		while ((phrase != null) && (!"".equals(phrase))) {

			String restPhrase = parseForSpecialWord(phrase, tokensList);

			if (restPhrase == null) {
				restPhrase = parseForDelimiters(phrase, tokensList);
			}

			if (restPhrase == null) {
				restPhrase = CharacterToken.TOKEN.accept(phrase, tokensList);
			}

			phrase = restPhrase;

		}

		return tokensList;
	}

	static private String parseForSpecialWord(String phrase, List<Token> tokensList) {

		// Special character must have an delimiter in front
		int size = tokensList.size();
		if (size > 0) {
			if (!(tokensList.get(size - 1) instanceof DelimiterToken)) {
				return null;
			}
		}

		return parseForTokens(phrase, tokensList, SpecialWordToken.All);

	}

	static private String parseForDelimiters(String phrase, List<Token> tokensList) {
		return parseForTokens(phrase, tokensList, DelimiterToken.All);
	}

	private static String parseForTokens(String phrase, List<Token> tokensList, Token[] tokens) {
		for (Token token : tokens) {
			String restPhrase = token.accept(phrase, tokensList);
			if (restPhrase != null) {
				return restPhrase;
			}
		}

		return null;
	}

}
