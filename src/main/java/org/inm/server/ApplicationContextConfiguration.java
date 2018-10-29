package org.inm.server;

import org.inm.interest.InterestStore;
import org.inm.subscription.SubscriberStore;
import org.inm.website.ChangeDetectionExecutor;
import org.inm.website.WebsiteStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Creates the spring beans for autowiring
 */
@Configuration
public class ApplicationContextConfiguration {

        @Bean
        public InterestStore interestStore () {
            return new InterestStore();
        }
        
        @Bean
        public WebsiteStore websiteStore () {
            return new WebsiteStore();
        }
        
        @Bean
        public SubscriberStore subscriberStore () {
            return new SubscriberStore();
        }
        
        @Bean
        public ChangeDetectionExecutor changeDetectionExecutor() {
        	return new ChangeDetectionExecutor();
        }

}