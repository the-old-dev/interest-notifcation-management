package org.inm.change.dailydose;

import org.htmlcleaner.CleanerProperties;

import org.inm.change.WebSiteReader;

public class DailyDoseOffersReader extends WebSiteReader {
	
	protected void beforeHtmlCleaning(CleanerProperties props) {
		props.setPruneTags("head,script,style,li,thead,tfoot,span,noscript,form,p");
	}
	
}
