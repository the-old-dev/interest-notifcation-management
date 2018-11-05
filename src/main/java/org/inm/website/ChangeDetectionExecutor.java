package org.inm.website;

import org.inm.interest.InterestStore;
import org.inm.interest.LocationStore;
import org.inm.util.NullCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeDetectionExecutor {

	private Logger logger = LoggerFactory.getLogger(ChangeDetectionExecutor.class);

	public void execute(WebsiteStore websiteStore, InterestStore interestStore, LocationStore locationStore) {

		NullCheck.NotNull("websiteStore", websiteStore);
		NullCheck.NotNull("interestStore", interestStore);

		logger.info("Running change detection for all websites");
		for (Website website : websiteStore.findAll()) {
			executeChangeDetection(website, interestStore, locationStore);
		}

	}

	private void executeChangeDetection(Website website, InterestStore interestStore, LocationStore locationStore) {

		logger.info("Running change detection for website:=" + website.getUrl());

		for (Subscribable subscribable : website.getSubscribables()) {

			try {
				AbstractChangeDetector detector = getChangeDetector(subscribable);
				detector.initialize(interestStore, locationStore, website, subscribable);
				detector.run();
			} catch (InstantiationException e) {
				logger.error("Instantiation failed for the change detector of the subscribable:="
						+ subscribable.getUrl().toExternalForm(), e);
			} catch (IllegalAccessException e) {
				logger.error("Access failed for the change detector of the subscribable:="
						+ subscribable.getUrl().toExternalForm(), e);
			}

		}

		logger.info("Running change detection for website:=" + website.getUrl() + " finished.");

	}

	private AbstractChangeDetector getChangeDetector(Subscribable subscribable)
			throws InstantiationException, IllegalAccessException {
		return (AbstractChangeDetector) subscribable.getChangeDetectionClass().newInstance();
	}

}
