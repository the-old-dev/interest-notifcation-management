package org.inm.interest.ors.fuzzy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ParserAndRendererTestCase {

	private void check(String phrase, SearchPhrase[] expectedPhrases, List<Class<?>[]> expectedTokenClassChains) {

		List<List<Token>> tokenChains = Parser.parse(phrase);

		int i = 0;
		for (List<Token> tokenChain : tokenChains) {
			check(tokenChain, expectedPhrases[i], expectedTokenClassChains.get(i));
			i = i + 1;
		}
	}

	private void check(String phrase, SearchPhrase expected, Class<?>[] expectedTokenClasses) {

		List<List<Token>> tokenChains = Parser.parse(phrase);
		List<Token> tokenChain = tokenChains.get(0);
		this.check(tokenChain, expected, expectedTokenClasses);

	}

	private void check(List<Token> tokenChain, SearchPhrase expected, Class<?>[] expectedTokenClasses) {

		// test the tokens
		Iterator<Token> tokenIterator = tokenChain.iterator();
		for (Class<?> expectedTokenClass : expectedTokenClasses) {
			Assert.assertTrue(tokenIterator.hasNext());
			Assert.assertEquals(expectedTokenClass, tokenIterator.next().getClass());
		}
		Assert.assertFalse(tokenIterator.hasNext());

		// test the phrase
		SearchPhrase actual = Renderers.renderWithStrictDelimiting(tokenChain);

		// check rendered
		deepEquals(expected, actual);
	}

	private void deepEquals(SearchPhrase expected, SearchPhrase actual) {

		Assert.assertEquals(expected.getPhrase(), actual.getPhrase());
		Assert.assertEquals(expected.getCountryCode(), actual.getCountryCode());
	}

	@Test
	public void testStEngelmarimPongau() throws Exception {

		// create expected ones
		String phrase = "St. Engelmar i. Pongau";
		SearchPhrase expected = new SearchPhrase();
		expected.appendToPhrase(phrase);

		// 1) No modifcation
		Class<?>[] expectedTokenClasses = new Class[] {
				PrefixToken.class, DelimiterToken.class, WordToken.class, DelimiterToken.class, LocatedAtToken.class,
				DelimiterToken.class, WordToken.class
		};
		check(phrase, expected, expectedTokenClasses);

		// 2) Strict delimiting after rendering, not in the token class chain
		phrase = "St.Engelmar i.Pongau";
		expectedTokenClasses = new Class[] {
				PrefixToken.class, WordToken.class, DelimiterToken.class, LocatedAtToken.class, WordToken.class
		};
		check(phrase, expected, expectedTokenClasses);

	}

	@Test
	public void testUnsuitables() throws Exception {

		// Remove unsuitables
		String phrase = "München Sonstwo Versand schicken";
		SearchPhrase expected = new SearchPhrase();
		expected.appendToPhrase("München");

		Class<?>[] expectedTokenClasses = new Class[] {
				WordToken.class, DelimiterToken.class, DelimiterToken.class, DelimiterToken.class
		};
		check(phrase, expected, expectedTokenClasses);
	}

	@Test
	public void testWithCountry() throws Exception {

		// 1) Simple
		String phrase = "Paris Frankreich";
		SearchPhrase expected = new SearchPhrase("Paris", "FR");

		Class<?>[] expectedTokenClasses = new Class[] {
				WordToken.class, DelimiterToken.class, CountryToken.class
		};
		check(phrase, expected, expectedTokenClasses);

		// 1) Simple
		phrase = "Frankreich";
		expected = new SearchPhrase("", "FR");

		expectedTokenClasses = new Class[] {
				CountryToken.class
		};
		check(phrase, expected, expectedTokenClasses);

	}

	@Test
	public void testWithSeparators() throws Exception {

		String phrase = "Regensburg oder Mainz am Main | Bozen Italien";

		SearchPhrase[] expectedPhrases = new SearchPhrase[] {
				new SearchPhrase("Regensburg"), new SearchPhrase("Mainz a. Main"),
				new SearchPhrase("Bozen", CountryToken.ITALY)
		};

		List<Class<?>[]> expectedTokenClassChains = new ArrayList<Class<?>[]>();
		expectedTokenClassChains.add(new Class[] {
				WordToken.class, DelimiterToken.class
		});
		expectedTokenClassChains.add(new Class[] {
				DelimiterToken.class, WordToken.class, DelimiterToken.class, LocatedAtToken.class, DelimiterToken.class,
				WordToken.class,DelimiterToken.class
		});
		expectedTokenClassChains.add(new Class[] {
				DelimiterToken.class, WordToken.class, DelimiterToken.class, CountryToken.class
		});

		check(phrase, expectedPhrases, expectedTokenClassChains);
	}

}
