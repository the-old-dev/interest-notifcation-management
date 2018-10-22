package org.inm.change;

import java.io.IOException;
import java.net.URL;

import org.htmlcleaner.TagNode;

public abstract class AbstractReader {

	private URL url;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public abstract TagNode getActualDataAsXml() throws IOException;

}