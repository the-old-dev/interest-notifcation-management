package org.inm.server;

import java.util.List;

import org.inm.AfterApplicationStartup;
import org.inm.website.AbstractConfiguration;
import org.inm.website.ChangeDetectionExecutor;
import org.inm.website.WebsiteStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventListener {

	private Logger logger = LoggerFactory.getLogger(ChangeDetectionExecutor.class);

	@Autowired
	private WebsiteStore websiteStore;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("Now Configuring Change Detectors");
		// Scan for annotations
		SpringAnnotatedClassScanner<AfterApplicationStartup> scanner = new SpringAnnotatedClassScanner<>();
		try {
			List<Class<?>> founds = scanner.findAnnotatedClasses("org.inm.website", AfterApplicationStartup.class);
			for (Class<?> found : founds) {
				if (AbstractConfiguration.class.isAssignableFrom(found) ) {
					AbstractConfiguration configuration = (AbstractConfiguration) found.newInstance();
					configuration.setStore(websiteStore);
					logger.info("Now running Configuration:="+configuration.getClass().getCanonicalName());
					configuration.run();
				}
			}
		} catch (Exception e) {
			logger.error("Error during Configuring Change Detectors", e);
		}
	}
}
