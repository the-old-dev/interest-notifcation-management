package org.inm.website;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.htmlcleaner.TagNode;
import org.inm.interest.Interest;
import org.inm.interest.InterestStore;
import org.inm.interest.LocationStore;
import org.inm.util.NullCheck;

public abstract class AbstractChangeDetector implements Runnable {

	protected Website website;
	protected AbstractReader reader = null;
	protected AbstractTransformator transformator = null;
	protected InterestStore store = null;
	protected LocationStore locationStore = null;
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

	public void initialize(InterestStore store, LocationStore locationStore, Website website, Subscribable subscribable) {

		NullCheck.NotNull("store", store);
		NullCheck.NotNull("locationStore", locationStore);
		NullCheck.NotNull("website", website);
		NullCheck.NotNull("subscribable", subscribable);

		this.store = store;
		this.locationStore = locationStore;
		this.website = website;
		this.subscribable = subscribable;

		this.transformator.setWebsite(this.website);
		this.reader.setWebsite(this.website);
		this.reader.setUrl(this.subscribable.getUrl());
		
	}

	@Override
	public void run() {

		checkPreparation();

		try {

			TagNode html = this.reader.next();
			List<Interest> entityList = this.transformator.transform(html);
			
			while (entityList.size() > 0) {

				// Insert the new entities into the store
				for (Interest news : entityList) {

					news.setDetectedOn(this.subscribable);

					if (!store.exists(news)) {
						store.insert(news);
					} else {
						Interest existing = this.store.findByIdField(news.getUrl());
						boolean stop = stop(news, existing);
						if (stop) {
							return;
						}
						store.update(news);
					}

				}

				html = this.reader.next();
				entityList = this.transformator.transform(html);
				
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

	protected abstract boolean stop(Interest readed, Interest existing);

}
