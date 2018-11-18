package org.inm.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.inm.interest.InterestStore;
import org.inm.interest.LocationService;
import org.inm.util.NullCheck;
import org.inm.website.ChangeDetectionExecutor;
import org.inm.website.WebsiteStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private WebsiteStore websiteStore;

	@Autowired
	private ChangeDetectionExecutor changeDetectionExecutor;

	@Autowired
	InterestStore interestStore;

	@Autowired
	LocationService locationService;

	// Run once a day
	@Scheduled(fixedDelay = 86400000)
	public void runTasks() {

		log.info("Now {} running scheduled tasks ...", dateFormat.format(new Date()));

		NullCheck.NotNull("websiteStore", websiteStore);
		NullCheck.NotNull("interestStore", interestStore);
		NullCheck.NotNull("locationService", locationService);

		changeDetectionExecutor.execute(websiteStore, interestStore, locationService);

	}

}