package org.inm.subscription;

import java.io.Serializable;
import java.net.URL;

public class Subscription implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String websiteUrl;
	private URL subscribableUrl;
	private String[] rules;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public URL getSubscribableUrl() {
		return subscribableUrl;
	}

	public void setSubscribableUrl(URL subscribableUrl) {
		this.subscribableUrl = subscribableUrl;
	}

	public String[] getRules() {
		return rules;
	}

	public void setRule(String[] rules) {
		this.rules = rules;
	}

}
