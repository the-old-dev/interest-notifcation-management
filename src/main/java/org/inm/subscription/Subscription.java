package org.inm.subscription;

import java.io.Serializable;
import java.net.URL;

public class Subscription implements Serializable {

	private static final long serialVersionUID = 1L;

    // Uniqueness fields:
    private URL subscribableUrl;
    private String email;

    
	private String name;
	private String[] interestsUrls;
	private String[] rules;

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

	public String[] getRules() {
		return rules;
	}

	public void setRule(String[] rules) {
		this.rules = rules;
	}

}
