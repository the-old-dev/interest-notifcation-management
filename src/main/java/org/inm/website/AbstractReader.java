package org.inm.website;

import java.io.IOException;
import java.net.URL;

import org.htmlcleaner.TagNode;

public abstract class AbstractReader {

	private URL url;
	private Website website;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public abstract TagNode next() throws IOException;

}