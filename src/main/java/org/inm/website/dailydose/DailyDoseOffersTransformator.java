package org.inm.website.dailydose;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.inm.interest.Interest;
import org.inm.website.AbstractTransformator;

class DailyDoseOffersTransformator extends AbstractTransformator {

	public final static String expression = "-?\\d+";

	@Override
	public List<Interest> transform(TagNode xhmlNode) throws IOException {

		long visitedTime = System.currentTimeMillis();

		List<Interest> list = new ArrayList<Interest>();

		try {

			Object[] offerRows = xhmlNode.evaluateXPath(
					"//*[@id='dd_inhalt']/table[1]/tbody/tr/td/table[4]/tbody/tr/td/table[2]/tbody/tr[position()>1]");

			for (Object found : offerRows) {
				Interest offer = transform(visitedTime, found);
				if (offer != null) {
					list.add(offer);
				}
			}

		} catch (Exception e) {
			throw new IOException(e);
		}

		return list;
	}

	protected Interest transform(long visitedTime, Object found) {

		if (found == null) {
			return null;
		}

		try {

			TagNode row = (TagNode) found;

			String url = getUrl(row);

			if (url == null) {
				return null;
			}

			String artikel = getArtikel(row);
			if (artikel == null) {
				return null;
			}

			String beschreibung = getBeschreibung(url);
			Object preis = getPreis(row);
			String ort = getOrt(row);
			Long datum = getDatum(row);
			URL bildURL = getImage(row);

			Interest offer = new Interest();

			offer.setUrl(url);
			offer.setTitle(artikel);
			offer.setLastUpdated(visitedTime);
			
			offer.getDetails().put("price", preis);
			offer.getDetails().put("description", beschreibung);
			offer.getDetails().put("date", datum);
			offer.getDetails().put("location", ort);
			offer.getDetails().put("imageURL", bildURL);

			return offer;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected Double extractPreis(String price) {

		Double extracted = null;

		Pattern p = Pattern.compile(expression);
		Matcher m = p.matcher(price);

		while (m.find()) {
			Double found = Math.abs(new Double(m.group()));
			if (extracted == null) {
				extracted = found;
			} else {

				if (found > extracted) {
					extracted = found;
				}
			}
		}

		return extracted;
	}

	private String getBeschreibung(String url) throws IOException, XPatherException {

		DailyDoseOffersReader reader = new DailyDoseOffersReader();
		reader.setUrl(new URL(url));

		TagNode page = reader.getActualDataAsXml();

		if (page == null) {
			return null;
		}

		String xPath = "//*[@id='dd_inhalt']/table[1]/tbody/tr/td/table[4]/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr[1]/td/table/tbody/tr/td[1]/table/tbody/tr/td[1]/table[1]/tbody/tr/td";

		Object[] result = page.evaluateXPath(xPath);

		if (result == null || result.length == 0) {
			return null;
		}

		TagNode descriptionElement = (TagNode) result[0];
		CharSequence text = descriptionElement.getText();
		if (text == null) {
			return null;
		}

		String description = text.toString();

		return description;

	}

	private URL getImage(TagNode row) throws XPatherException, IOException {

		Object[] result = row.evaluateXPath("//td[7]/table/tbody/tr/td/a/img/@src");

		if (result == null || result.length == 0) {
			return null;
		}

		String src = result[0].toString();
		if (src == null) {
			return null;
		}

		return new URL(getWebsite().getUrl() + src);

	}

	private long getDatum(TagNode row) throws XPatherException {
		String datumString = getColumnText(row, 6);
		try {
			return DateFormat.getDateInstance().parse(datumString).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	private String getOrt(TagNode row) throws XPatherException {
		return getColumnText(row, 5);
	}

	private Object getPreis(TagNode row) throws XPatherException {
		String columnValue = getColumnText(row, 4);
		
		Double preis = extractPreis(columnValue);
		if (preis != null) {
			return preis;
		}
		
		return columnValue;
	}

	private String getUrl(TagNode row) throws XPatherException, MalformedURLException {

		TagNode a = getHref(row, 3);
		if (a == null) {
			return null;
		}

		String href = a.getAttributeByName("href");
		if (href == null) {
			return null;
		}

		return getWebsite().getUrl() + href;
	}

	private String getArtikel(TagNode row) throws XPatherException {

		TagNode a = getHref(row, 3);
		if (a == null) {
			return null;
		}

		CharSequence text = a.getText();
		if (text == null) {
			return null;
		}

		return text.toString();
	}

	private TagNode getHref(TagNode row, int columnNumber) throws XPatherException {

		TagNode column = getColumn(row, columnNumber);
		if (column == null) {
			return null;
		}

		return getFirstElement(column, "//a");

	}

	String getColumnText(TagNode row, int column) throws XPatherException {

		TagNode columnNode = getColumn(row, column);

		if (columnNode != null) {
			CharSequence text = columnNode.getText();
			if (text == null) {
				return null;
			}
			return text.toString();
		}

		return null;
	}

	private TagNode getColumn(TagNode row, int column) throws XPatherException {

		String xPathExpression = "//td[" + column + "]";

		TagNode columnNode = getFirstElement(row, xPathExpression);

		return columnNode;
	}

	private TagNode getFirstElement(TagNode startNode, String xPathExpression) throws XPatherException {

		TagNode columnNode = null;

		Object[] columnNodes = startNode.evaluateXPath(xPathExpression);

		if (columnNodes.length > 0) {
			columnNode = (TagNode) columnNodes[0];
		}

		return columnNode;
	}

}