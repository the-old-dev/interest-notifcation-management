package org.inm.website;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.htmlcleaner.TagNode;
import org.inm.interest.Interest;
import org.inm.interest.InterestStore;
import org.inm.util.NullCheck;

public abstract class AbstractChangeDetector implements Runnable {

	protected Website website;
	protected AbstractReader reader = null;
	protected AbstractTransformator transformator = null;
	protected InterestStore store = null;
	private Subscribable subscribable;

	protected Logger LOGGER = null;

	public AbstractChangeDetector() {
		this.LOGGER = Logger.getLogger(this.getClass().getName());
	}

	protected void setReader(AbstractReader reader) {
		NullCheck.NotNull("reader", reader);
		reader.setWebsite(this.website);
		this.reader = reader;
	}

	protected void setTransformator(AbstractTransformator transformator) {
		NullCheck.NotNull("transformator", transformator);
		transformator.setWebsite(this.website);
		this.transformator = transformator;
	}

	public void initialize(InterestStore store, Website website, Subscribable subscribable) {

		NullCheck.NotNull("store", store);
		NullCheck.NotNull("website", website);
		NullCheck.NotNull("subscribable", subscribable);

		this.store = store;
		this.website = website;
		this.subscribable = subscribable;

		this.transformator.setWebsite(this.website);
		this.reader.setWebsite(this.website);
	}

	@Override
	public void run() {

		checkPreparation();

		Iterator<Interest> entityList = readAndTransform(this.subscribable.getUrl());

		// Insert the new entities into the store
		try {
			while (entityList.hasNext()) {
				Interest news = entityList.next();
				news.setDetectedOn(this.subscribable);
				if (!store.exists(news)) {
					store.insert(news);
				} else {
					store.update(news);
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error occured while executing ", e);
		}
	}

	protected void checkPreparation() {
		NullCheck.NotNull("reader", reader);
		NullCheck.NotNull("transformator", transformator);
		NullCheck.NotNull("store", store);
		NullCheck.NotNull("website", website);
	}

	protected Iterator<Interest> readAndTransform(URL url) {

		TagNode xhmlNode = null;
		List<Interest> entityList = null;

		// Call the reader
		try {
			reader.setUrl(url);
			xhmlNode = reader.getActualDataAsXml();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error occured while reading via " + reader.getClass().getName(), e);
		}

		// Call the transformator
		try {
			entityList = transformator.transform(xhmlNode);
		} catch (Exception e) {
			LOGGER.log(Level.ALL, "Error occured while transforming via " + transformator.getClass().getName(), e);
		}

		return entityList.iterator();
	}

}
