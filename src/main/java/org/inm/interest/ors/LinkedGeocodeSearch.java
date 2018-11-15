package org.inm.interest.ors;

import org.inm.interest.Location;

public abstract class LinkedGeocodeSearch  {

	private LinkedGeocodeSearch nextService;

	public LinkedGeocodeSearch append(LinkedGeocodeSearch linkable) {
		setLinkable(linkable);
		return linkable;
	}

	protected abstract Location getLocation(String name, String countryCode);
	
	protected void setLinkable(LinkedGeocodeSearch linkable) {
		this.nextService = linkable;
	}

	public LinkedGeocodeSearch getNextService() {
		return nextService;
	}

}
