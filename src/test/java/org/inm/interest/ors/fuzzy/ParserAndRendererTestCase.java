package org.inm.interest.ors.fuzzy;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ParserAndRendererTestCase {

	@Test
	public void testSimple() throws Exception {

		// 1) No modifcation
		String phrase = "St. Engelmar i. Pongau";
		SearchPhrase expected = new SearchPhrase();
		expected.appendToPhrase(phrase);

		// parse
		List<Token> tokens = Parser.parse(phrase);

		// check parsed
		Iterator<Token> iterator = tokens.iterator();
		Assert.assertTrue(iterator.next() instanceof SpecialWordToken);
		Assert.assertTrue(iterator.next() instanceof DelimiterToken);
		Assert.assertTrue(iterator.next() instanceof WordToken);
		Assert.assertTrue(iterator.next() instanceof DelimiterToken);
		Assert.assertTrue(iterator.next() instanceof SpecialWordToken);
		Assert.assertTrue(iterator.next() instanceof DelimiterToken);
		Assert.assertTrue(iterator.next() instanceof WordToken);
		Assert.assertFalse(iterator.hasNext());

		// render
		SearchPhrase actual = Renderers.renderWithStrictDelimiting(tokens);

		// check rendered
		deepEquals(expected, actual);

		// 2) Strict delimiting
		tokens = Parser.parse("St.Engelmar i.Pongau");

		iterator = tokens.iterator();
		Assert.assertTrue(iterator.next() instanceof SpecialWordToken);
		Assert.assertTrue(iterator.next() instanceof WordToken);
		Assert.assertTrue(iterator.next() instanceof DelimiterToken);
		Assert.assertTrue(iterator.next() instanceof SpecialWordToken);
		Assert.assertTrue(iterator.next() instanceof WordToken);
		Assert.assertFalse(iterator.hasNext());

		// render
		actual = Renderers.renderWithStrictDelimiting(tokens);

		deepEquals(expected, actual);

		// 3) Strict delimiting more complex

		tokens = Parser.parse("St.Engelmari.Pongau");
		expected = new SearchPhrase();
		expected.appendToPhrase("St. Engelmari.Pongau");

		iterator = tokens.iterator();
		Assert.assertTrue(iterator.next() instanceof SpecialWordToken);
		Assert.assertTrue(iterator.next() instanceof WordToken);
		Assert.assertFalse(iterator.hasNext());

		// render
		actual = Renderers.renderWithStrictDelimiting(tokens);

		deepEquals(expected, actual);

		// 3) Strict delimiting, with overlappings

		tokens = Parser.parse("St.Engelmam.Pongim");
		expected = new SearchPhrase();
		expected.appendToPhrase("St. Engelmam.Pongim");

		iterator = tokens.iterator();
		Assert.assertTrue(iterator.next() instanceof SpecialWordToken);
		Assert.assertTrue(iterator.next() instanceof WordToken);
		Assert.assertFalse(iterator.hasNext());

		// render
		actual = Renderers.renderWithStrictDelimiting(tokens);

		deepEquals(expected, actual);

	}

	private void deepEquals(SearchPhrase expected, SearchPhrase actual) {
		Assert.assertEquals(expected.getPhrase(), actual.getPhrase());
		Assert.assertEquals(expected.getCountryCode(), actual.getCountryCode());
	}

	@Test
	public void testWithCountry() throws Exception {

		// 1) Simple
		String phrase = "Paris Frankreich";
		SearchPhrase expected = new SearchPhrase();
		expected.appendToPhrase("Paris");
		expected.setCountryCode("FR");

		// parse
		List<Token> tokens = Parser.parse(phrase);

		// check parsed
		Iterator<Token> iterator = tokens.iterator();
		Assert.assertTrue(iterator.next() instanceof WordToken);
		Assert.assertTrue(iterator.next() instanceof DelimiterToken);
		Assert.assertTrue(iterator.next() instanceof CountryToken);
		Assert.assertFalse(iterator.hasNext());

		// render
		SearchPhrase actual = Renderers.renderWithStrictDelimiting(tokens);

		deepEquals(expected, actual);

	}
}
