package org.inm.subscription;

import java.io.Serializable;
import java.net.URL;

public class Subscription implements Serializable {

	private static final long serialVersionUID = 1L;

	// Uniqueness fields:
	private URL subscribableUrl;
	private String email;

	// Data fields
	private String name;
	private FilterDefinition rule;
	private String[] interestsUrls;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public URL getSubscribableUrl() {
		return subscribableUrl;
	}

	public void setSubscribableUrl(URL subscribableUrl) {
		this.subscribableUrl = subscribableUrl;
	}

	public String[] getInterestsUrls() {
		return interestsUrls;
	}

	public void setInterestsUrls(String[] interestsUrls) {
		this.interestsUrls = interestsUrls;
	}

	public FilterDefinition getRule() {
		return rule;
	}

	public void setRule(FilterDefinition rule) {
		this.rule = rule;
	}

}
