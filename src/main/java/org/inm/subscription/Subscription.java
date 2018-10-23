package org.inm.subscription;

import java.io.Serializable;
import java.net.URL;

public class Subscription implements Serializable {

	private static final long serialVersionUID = 1L;

	private String websiteUrl;
	private URL subscribableUrl;
	private String rule;

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

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

}
