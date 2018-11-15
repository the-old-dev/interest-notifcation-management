package org.inm.interest.ors;

import java.util.Map;

import org.inm.interest.Location;

public class MockGeocodeSearch extends LinkedGeocodeSearch {

	private Map<String, Location> responses;
	private int calls;

	public MockGeocodeSearch(Map<String, Location> responses) {
		this.responses = responses;
		this.calls = 0;
	}

	@Override
	public Location getLocation(String name, String countryCode) {
		this.calls = this.calls + 1;
		return responses.get(name);
	}

	public int getCalls() {
		return calls;
	}

}
