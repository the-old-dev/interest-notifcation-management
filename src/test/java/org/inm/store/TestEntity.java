package org.inm.store;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import org.dizitart.no2.objects.Id;

public class TestEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String url;

	private String name;

	public void setUrl(String url) {
		
		// Try to create an URL Object
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
		
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
