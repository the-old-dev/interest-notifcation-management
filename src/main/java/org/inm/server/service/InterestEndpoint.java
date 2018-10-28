package org.inm.server.service;

import java.util.List;
import java.util.Map;

import org.inm.interest.Interest;
import org.inm.interest.InterestStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterestEndpoint {

	@Autowired
	InterestStore interestStore;

	@RequestMapping("/api/interest")
	public List<Interest> interest() {
		return interestStore.findAllAsList();
	}

	/**
	 * Reacts to URLs like: http://localhost:8080/api/interest/?detectedOn.subCategory="Foils"
	 * @param requestParameter
	 * @return
	 */
	@RequestMapping("/api/interest/")
	public List<Interest> interest(@RequestParam Map<String, String> requestParameter) {
		try {
			String fieldName = requestParameter.keySet().iterator().next();
			String value = requestParameter.get(fieldName);
			value = value.substring(1, value.length() - 1);
			return interestStore.findByField(fieldName, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
