package org.inm.server.service;

import java.util.List;

import org.inm.website.Website;
import org.inm.website.WebsiteStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsiteEndpoint {
	
	@Autowired
	WebsiteStore websiteStore;
	
	@RequestMapping("/api/website")
	public List<Website> website() {
		return websiteStore.findAllAsList();
	}

}
