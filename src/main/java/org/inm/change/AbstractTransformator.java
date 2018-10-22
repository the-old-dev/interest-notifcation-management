package org.inm.change;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.h2.util.IOUtils;
import org.htmlcleaner.TagNode;

import org.inm.interests.Interest;

public abstract class AbstractTransformator {

	public static class Download {
		public String contentType;
		public byte[] content;
	}

	public abstract List<Interest> transform(TagNode xhmlNode) throws IOException;

	// #### Utitlity Methods for subclasses #####

	protected static Download downloadFile(URL url) throws IOException {
		return downloadFile(url, 10000);
	}

	protected static Download downloadFile(URL url, int timeout) throws IOException {

		URLConnection conn = null;

		// Connect with timeout
		conn = url.openConnection();
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);
		conn.connect();

		// read bytes
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(conn.getInputStream(), baos);
		Download download = new Download();
		download.content = baos.toByteArray();
		download.contentType = conn.getContentType();

		return download;

	}

}
