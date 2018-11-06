package org.inm.website;

import org.inm.interest.InterestStore;
import org.inm.interest.LocationService;
import org.inm.util.NullCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChangeDetectionExecutor {

	private Logger logger = LoggerFactory.getLogger(ChangeDetectionExecutor.class);

	public void execute(WebsiteStore websiteStore, InterestStore interestStore, LocationService locationService) {

		NullCheck.NotNull("websiteStore", websiteStore);
		NullCheck.NotNull("interestStore", interestStore);
		NullCheck.NotNull("locationService", locationService);

		logger.info("Running change detection for all websites");
		for (Website website : websiteStore.findAll()) {
			executeChangeDetection(website, interestStore, locationService);
		}

	}

	private void executeChangeDetection(Website website, InterestStore interestStore, LocationService locationService) {

		logger.info("Running change detection for website:=" + website.getUrl());

		for (Subscribable subscribable : website.getSubscribables()) {

			try {
				AbstractChangeDetector detector = getChangeDetector(subscribable);
				detector.initialize(interestStore, locationService, website, subscribable);
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
