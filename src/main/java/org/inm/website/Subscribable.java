package org.inm.website;

import java.io.Serializable;
import java.net.URL;

public class Subscribable implements Serializable {

	private static final long serialVersionUID = 1L;

	// ### unique
	private URL url;

	private String name;
	private String category;
	private String subCategory;
	private String websiteUrl;
	private Class<?> changeDetectionClass;

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public Class<?> getChangeDetectionClass() {
		return changeDetectionClass;
	}

	public void setChangeDetectionClass(Class<?> changeDetectionClass) {
		this.changeDetectionClass = changeDetectionClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

}
