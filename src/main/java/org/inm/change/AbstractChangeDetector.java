package org.inm.change;

import java.net.MalformedURLException;
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

	protected AbstractReader reader = null;
	protected AbstractTransformator transformator = null;
	protected InterestStore store = null;
	protected URL url = null;

	protected Logger LOGGER = null;

	public AbstractChangeDetector() {
		this.LOGGER = Logger.getLogger(this.getClass().getName());
	}

	public void setReader(AbstractReader reader) {
		NullCheck.NotNull("reader", reader);
		this.reader = reader;
	}

	public void setTransformator(AbstractTransformator transformator) {
		NullCheck.NotNull("transformator", transformator);
		this.transformator = transformator;
	}

	public void setStore(InterestStore store) {
		NullCheck.NotNull("store", store);
		this.store = store;
	}

	@Override
	public void run() {

		NullCheck.NotNull("url", url);
		NullCheck.NotNull("reader", reader);
		NullCheck.NotNull("transformator", transformator);
		NullCheck.NotNull("store", store);

		Iterator<Interest> entityList = readAndTransform();

		// Insert the new entities into the store
		try {
			while (entityList.hasNext()) {
				Interest news = entityList.next();
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

	protected void setURL(String urlString) {
		try {
			this.url = new URL(urlString);
		} catch (MalformedURLException e) {
			LOGGER.log(Level.ALL, "Can not set the url into the reader!", e);
		}

	}

	protected Iterator<Interest> readAndTransform() {

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
