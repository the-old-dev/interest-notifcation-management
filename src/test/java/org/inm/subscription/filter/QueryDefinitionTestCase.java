package org.inm.subscription.filter;

import org.junit.Test;
import org.junit.Assert;
import org.dizitart.no2.objects.ObjectFilter;
import org.inm.subscription.FilterDefinition;
import org.inm.util.Json2ObjectReader;

public class QueryDefinitionTestCase {
    
    @Test
    public void testJsonDefinition() throws Exception {
    	
        FilterDefinition definition = 
            Json2ObjectReader.read(FilterDefinition.class.getName()+".json", FilterDefinition.class);
        
        Assert.assertNotNull(definition);
        
        ObjectFilter filter = definition.create();
        Assert.assertNotNull(filter);
        
        String expected = "AndObjectFilter(filters=[EqualsObjectFilter(field=a, value=b), LessThanObjectFilter(field=b, value=1), LesserEqualObjectFilter(field=c, value=2)])";
        Assert.assertEquals(expected, filter.toString());
        
    }
}
