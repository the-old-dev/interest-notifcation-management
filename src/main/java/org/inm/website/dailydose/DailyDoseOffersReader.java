package org.inm.website.dailydose;

import org.htmlcleaner.CleanerProperties;
import org.inm.website.WebSiteReader;

public class DailyDoseOffersReader extends WebSiteReader {
	
	protected void beforeHtmlCleaning(CleanerProperties props) {
		props.setPruneTags("head,script,style,li,thead,tfoot,span,noscript,form,p");
		props.setCharset("iso-8859-1");
	}
	
}
