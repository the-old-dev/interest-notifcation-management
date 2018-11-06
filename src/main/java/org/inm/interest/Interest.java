package org.inm.interest;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dizitart.no2.objects.Id;
import org.inm.website.Subscribable;

public class Interest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String url;

	private Subscribable detectedOn;
	private String title;
	private long lastUpdated;
	private List<Location> locations;
	private HashMap<String, Object> details;

	public Interest() {
		this.details = new HashMap<>();
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Subscribable getDetectedOn() {
		return detectedOn;
	}

	public void setDetectedOn(Subscribable detectedOn) {
		this.detectedOn = detectedOn;
	}

	public void setDetails(HashMap<String, Object> details) {
		this.details = details;
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
