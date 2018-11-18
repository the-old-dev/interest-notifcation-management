package org.inm.interest.ors.fuzzy;

import java.util.ArrayList;
import java.util.List;

import org.inm.util.EmtyCheck;

/**
 * The parsing is done in three phases:
 * 
 * 1) Create a token list with the elementary elements (Separator, Delimiter, Character)
 * 
 * 2) Build a new list and replace the CharacterTokens Sequence with the WordToken tokens
 * 
 * 3) Build a new list and replace Words which are countries with CountryTokens and special words with SpecialWord
 * tokens
 * 
 * 4) Build new lists separated by the separator tokens.
 * 
 * @author user
 *
 */
public class Parser {

	static abstract class TokenVisitor {

		protected List<Token> newTokens;

		TokenVisitor() {
			this.newTokens = new ArrayList<Token>();
		}

		void visit(Token token) {

			newTokens.add(token);
		}

		void visit(CharacterToken token) {

			visit((Token) token);
		}

		void visit(WordToken token) {

			visit((Token) token);
		}

		void visit(SeparatorToken token) {

			visit((Token) token);
		}

		public List<Token> getNewTokens() {

			return newTokens;
		}

	}

	/**
	 * Build a new list and replace the CharacterTokens Sequence with the WordToken tokens.
	 * 
	 * @param phrase
	 * @return
	 */
	static class SecondPhaseVisitor extends TokenVisitor {

		private WordToken word = null;

		void visit(CharacterToken token) {

			if (word == null) {
				word = new WordToken((CharacterToken) token);
				newTokens.add(word);
			} else {
				word.append((CharacterToken) token);
			}
		}

		void visit(Token token) {

			if (word != null) {
				word = null;
			}
			super.visit(token);
		}

	}

	/**
	 * Build a new list and:
	 * 
	 * - replace Words which are countries with CountryTokens.
	 * 
	 * - remove unsuitable tokens
	 * 
	 * @param parseSecondPhase
	 * @return
	 */
	static class ThirdPhaseVisitor extends TokenVisitor {

		@Override
		void visit(WordToken token) {

			String wordPhrase = token.getfuzzies()[0];

			// omit unsuitable tokens in the new list
			if (UnsuitableToken.All.accept(wordPhrase, this.newTokens) != null) {
				this.newTokens.remove(this.newTokens.size() - 1);
				return;
			}

			// add the token if it is a CountryToken
			if (CountryToken.All.accept(wordPhrase, this.newTokens) != null) {
				return;
			}

			// add the token if it is a prefix Token
			String restPhrase = PrefixToken.All.accept(wordPhrase, this.newTokens);
			if (restPhrase != null) {
				addAsWordToken(restPhrase);
				return;
			}

			// add the token if it is a located at Token
			restPhrase = LocatedAtToken.All.accept(wordPhrase, this.newTokens);
			if (restPhrase != null) {
				addAsWordToken(restPhrase);
				return;
			}

			// It is now clear, this is a simple word: simply add it
			this.newTokens.add(token);

		}

		protected void addAsWordToken(String restPhrase) {

			// add the rest as word
			if (!EmtyCheck.isEmpty(restPhrase)) {
				this.newTokens.add(new WordToken(restPhrase));
			}
		}

	}

	/**
	 * 4) Build new lists separated by the separator tokens.
	 * 
	 * @author user
	 *
	 */
	static class FourthPhaseVisitor extends TokenVisitor {

		private List<List<Token>> tokenChainList = new ArrayList<List<Token>>();

		@Override
		void visit(SeparatorToken token) {

			addToChain();
			this.newTokens = new ArrayList<Token>();
		}

		private void addToChain() {

			if (this.newTokens.size() > 0) {
				this.tokenChainList.add(this.newTokens);
			}
		}

		public List<List<Token>> getTokenChainList() {

			addToChain();
			return tokenChainList;
		}

	}

	private static final String CHARACTER_TOKEN = "CharacterToken";
	private static final String WORD_TOKEN = "WordToken";
	private static final String UNSUITABLE_TOKEN = "UnsuitableToken";
	private static final String SEPARATOR_TOKEN = "SeparatorToken";

	static List<List<Token>> parse(String phrase) {
		return parseFourthPhase(parseThirdPhase(parseSecondPhase(parseFirstPhase(phrase))));
	}

	private static List<List<Token>> parseFourthPhase(List<Token> thirdPhase) {

		return ((FourthPhaseVisitor) runVisitation(thirdPhase, new FourthPhaseVisitor())).getTokenChainList();
	}

	private static List<Token> parseSecondPhase(List<Token> firstPhaseList) {

		return runVisitation(firstPhaseList, new SecondPhaseVisitor()).getNewTokens();
	}

	private static List<Token> parseThirdPhase(List<Token> secondPhaseList) {

		return runVisitation(secondPhaseList, new ThirdPhaseVisitor()).getNewTokens();
	}

	private static TokenVisitor runVisitation(List<Token> tokens, TokenVisitor visitor) {

		for (Token token : tokens) {

			switch (token.getClass().getSimpleName()) {

				case WORD_TOKEN:
					visitor.visit((WordToken) token);
					break;

				case CHARACTER_TOKEN:
					visitor.visit((CharacterToken) token);
					break;

				case UNSUITABLE_TOKEN:
					visitor.visit((UnsuitableToken) token);
					break;

				case SEPARATOR_TOKEN:
					visitor.visit((SeparatorToken) token);
					break;

				default:
					visitor.visit(token);
					break;

			}

		}

		return visitor;

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

			String restPhrase = DelimiterToken.All.accept(phrase, tokensList);

			if (restPhrase == null) {
				restPhrase = SeparatorToken.All.accept(phrase, tokensList);
			}

			if (restPhrase == null) {
				restPhrase = CharacterToken.TOKEN.accept(phrase, tokensList);
			}

			phrase = restPhrase;

		}

		return tokensList;
	}

}
