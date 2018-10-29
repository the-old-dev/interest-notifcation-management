package org.inm.server;

import java.net.URL;
import org.arquillian.cube.openshift.impl.enricher.AwaitRoute;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class OpenShiftIT extends AbstractApplicationTest {
	
	@Test
	public void dummyTest() {}

    @AwaitRoute(path = "/health")
    @RouteURL("${app.name}")
    private URL baseURL;

    @Override
    public String baseURI() {
        return baseURL.toString();
    }
}
