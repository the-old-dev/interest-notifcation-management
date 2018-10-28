package org.inm.website;

import java.io.IOException;
import java.io.InputStream;

import org.inm.util.EmtyCheck;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractConfiguration implements Runnable {

	private WebsiteStore store;

	public WebsiteStore getStore() {
		return store;
	}

	public void setStore(WebsiteStore store) {
		this.store = store;
	}

	@Override
	public void run() {
		checkPreparations();
		Website configuration = readConfigurationFile();
		complete(configuration);
		insertInStoreIfnotExists(configuration);
	}

	protected Class<?> getDefaultChangeDetectorClass() {
		return null;
	}

	private void complete(Website configuration) {
		for (Subscribable subscribable : configuration.getSubscribables()) {

			// Set the change detection class if not already set
			if (subscribable.getChangeDetectionClass() == null) {
				Class<?> defaultClass = getDefaultChangeDetectorClass();
				if (defaultClass == null) {
					throw new RuntimeException("The subscribable:=" + subscribable.getUrl().toExternalForm()
							+ " has no configured change-detection-class nor a default one!");
				}
				subscribable.setChangeDetectionClass(defaultClass);
			}

			// Set the change detection class if not already set
			if (EmtyCheck.isEmpty(subscribable.getWebsiteUrl())) {
				subscribable.setWebsiteUrl(configuration.getUrl());
			}
			
		}
	}

	private void insertInStoreIfnotExists(Website configuration) {
		if (!this.store.exists(configuration)) {
			this.store.insert(configuration);
		}
	}

	private Website readConfigurationFile() {
		ObjectMapper objectMapper = new ObjectMapper();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String resourceName = this.getClass().getPackage().getName() + ".json";
		InputStream resourceStream = loader.getResourceAsStream(resourceName);
		try {
			return objectMapper.readValue(resourceStream, Website.class);
		} catch (JsonParseException e) {
			throw new RuntimeException("Can not parse the resource:=" + resourceName, e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Can not mapp to the Website class, with the resource:=" + resourceName, e);
		} catch (IOException e) {
			throw new RuntimeException("IO Exception occured, while reading the resource:=" + resourceName, e);
		}
	}

	private void checkPreparations() {
		if (this.store == null) {
			throw new IllegalStateException("No store set, can not run!");
		}
	}

}
