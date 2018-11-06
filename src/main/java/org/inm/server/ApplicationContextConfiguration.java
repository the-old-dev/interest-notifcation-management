package org.inm.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Creates the spring beans for autowiring
 */
@Configuration
@ComponentScan("org.inm")
@EnableAutoConfiguration
public class ApplicationContextConfiguration {
       


}