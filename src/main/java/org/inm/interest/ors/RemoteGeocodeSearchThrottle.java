package org.inm.interest.ors;

/**
 * This webservice has a usage plan with a rate limitation:
 * https://openrouteservice.org/plans/
 * 
 * Free Plan: - 2.500 Requests per day - up to 40 Requests per minute
 * 
 * This class does a throtteling to 1 request per second.
 */
class RemoteGeocodeSearchThrottle {
    
    private long lastCalled = 0l;    

	void throttle() throws InterruptedException {

		long current = System.currentTimeMillis();
		long timeDelta = current - this.lastCalled;

		if (timeDelta < 2000) {
			Thread.sleep(2000 - timeDelta);
		}

		this.lastCalled = System.currentTimeMillis();

	}
 
}
