package org.inm.website.dailydose;

import org.htmlcleaner.CleanerProperties;
import org.inm.website.WebSiteReader;
import org.htmlcleaner.TagNode;
import java.net.URL;
import java.net.MalformedURLException;

import java.io.IOException;

public class DailyDoseOffersReader extends WebSiteReader {
	
	private int offset = 0;
	
	protected void beforeHtmlCleaning(CleanerProperties props) {
		props.setPruneTags("head,script,style,li,thead,tfoot,span,noscript,form,p");
		props.setCharset("iso-8859-1");
	}
	
	public TagNode next() throws IOException {
        TagNode next = super.next();
        stepForward();
		return next;
	}

	private void stepForward() {
		this.offset = this.offset + 20;
		setUrl(getCurrentUrlString());
	}
	
	private URL getCurrentUrlString() {
		try {
			return new URL(this.getUrl().toExternalForm() + "&offset=" + offset);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
