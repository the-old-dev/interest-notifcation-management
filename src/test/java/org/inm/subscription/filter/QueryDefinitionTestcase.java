package org.inm.subscription.filter;

import org.junit.Test;
import org.junit.Assert;

import org.inm.util.Json2ObjectReader;

public class QueryDefinitionTestcase {
    
    @Test
    public void testJsonDefinition() throws Exception {
        QueryDefinition definition = 
            Json2ObjectReader.read(QueryDefinition.class.getName(), QueryDefinition.class);
        Assert.assertNotNull(definition);
    }
}
