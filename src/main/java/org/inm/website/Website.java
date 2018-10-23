package org.inm.website;

import java.io.Serializable;
import java.util.List;

import org.dizitart.no2.objects.Id;

public class Website implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String url;

	private List<Subscribable> subscribables;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Subscribable> getSubscribables() {
		return subscribables;
	}

	public void setSubscribables(List<Subscribable> subscribables) {
		this.subscribables = subscribables;
	}

}
