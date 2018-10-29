package org.inm.server;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringStartsWith.startsWith;

public abstract class AbstractApplicationTest {

    private static final String GREETING_PATH = "api/greeting";
    
    // @Test
    public void testGreetingEndpoint() {
        given()
           .baseUri(baseURI())
           .get(GREETING_PATH)
           .then()
           .statusCode(200)
           .body("content", startsWith("World"));
    }

    // @Test
    public void testGreetingEndpointWithNameParameter() {
        given()
           .baseUri(baseURI())
           .param("name", "John")
           .when()
           .get(GREETING_PATH)
           .then()
           .statusCode(200)
           .body("content", startsWith("John"));
    }
    
    protected abstract String baseURI();
}
