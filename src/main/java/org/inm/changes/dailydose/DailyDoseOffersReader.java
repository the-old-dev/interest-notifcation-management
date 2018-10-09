package org.inm.changes.dailydose;

import org.htmlcleaner.CleanerProperties;

import org.inm.core.WebSiteReader;

public class DailyDoseOffersReader extends WebSiteReader {
	
	protected void beforeHtmlCleaning(CleanerProperties props) {
		props.setPruneTags("head,script,style,li,thead,tfoot,span,noscript,form,p");
	}
	
}
