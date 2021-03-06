package org.inm.interest;

import java.io.Serializable;

import org.dizitart.no2.objects.Id;
import org.inm.util.EmtyCheck;

public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	private Double latitude;
	private Double longitude;

	public Location() {
	}

	public Location(String name, Double longitude, Double latitude) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public boolean isInDistance(Location location, double distance) {
		return distance(location) <= distance;
	}

	public double distance(Location location) {
		return distance(this.latitude, this.longitude, location.latitude, location.longitude, "K");
	}
	
	public boolean isUnlocated() {
	    	return 
	    	  EmtyCheck.isNullOrZero(this.getLatitude()) 
	    	      || EmtyCheck.isNullOrZero(this.getLongitude())
	    	      || EmtyCheck.isEmpty(this.getName());
	}

	public String toString() {
	    return "Location { name:="+getName()+", longitude:="+getLongitude()+", latitude:="+getLatitude()+" }";
	}

	private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return (dist);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts decimal degrees to radians : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts radians to decimal degrees : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

}
