package org.inm.server;


import org.junit.runner.RunWith;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocalTest extends AbstractApplicationTest {
    
    @Value("${local.server.port}")
    private int port;

    @Test
    public void dummyTest() {
        
    }


    @Override
    public String baseURI() {
        return String.format("http://localhost:%d", port);
    }
}

