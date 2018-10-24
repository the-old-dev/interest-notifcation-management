package org.inm.interest;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.dizitart.no2.objects.Id;

public class Interest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String url;

	private URL subscribable;
	private String category;
	private String subCategory;
	private String title;
	private long lastUpdated;
	private HashMap<String, Object> details;

	public Interest() {
		this.details = new HashMap<>();
	}

	public URL getSubscribable() {
		return subscribable;
	}

	public void setSubscribable(URL subscribable) {
		this.subscribable = subscribable;
	}

	public void setDetails(HashMap<String, Object> details) {
		this.details = details;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		checkURL(url);
		this.url = url;
	}

	private void checkURL(String url) {
		// Try to create an URL Object
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

}
