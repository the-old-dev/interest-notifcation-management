package org.inm.server;

import org.inm.interest.InterestStore;
import org.inm.website.ChangeDetectionExecutor;
import org.inm.website.WebsiteStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        public ChangeDetectionExecutor changeDetectionExecutor() {
        	return new ChangeDetectionExecutor();
        }

}