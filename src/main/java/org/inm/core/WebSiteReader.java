package org.inm.core;

import java.io.IOException;
import java.net.URL;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class WebSiteReader {
    
    private URL url;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
		/**
	 * Get Data from the internet and clean into valid xml.
	 * 
	 * @see de.im.changes.AbstractReader#getActualDataAsXml()
	 */
	public TagNode getActualDataAsXml() throws IOException {

		if (this.getUrl() == null) {
			throw new IOException("this.url must not be null");
		}

		// Set the http agent to a browser, because some websites or proxies do
		// not allow java or null as the agent ...
		System.setProperty("http.agent", "Chrome");

		// set some properties to non-default values
		CleanerProperties props = new CleanerProperties();
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);

		beforeHtmlCleaning(props);

		// do parsing
		return new HtmlCleaner(props).clean(this.getUrl());

	}

	protected void beforeHtmlCleaning(CleanerProperties props) {
		props.setPruneTags("script,style");
	}
    
}