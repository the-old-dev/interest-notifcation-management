package org.inm.changes.dailydose;

import java.net.URL;
import org.htmlcleaner.TagNode;

import org.junit.Test;
import org.junit.Assert;

public class DailyDoseReadingTestCase {
    
    @Test
    public void testOffersReading() throws Exception {
        
        // define variables
        TagNode xhtmlNode = null;
        DailyDoseOffersReader reader = new DailyDoseOffersReader();
        
        // initialise variables
        reader.setUrl(new URL(Constants.BASE_URL));
        
        // run case
        xhtmlNode = reader.getActualDataAsXml();
        
        // test case
        
        // readed not null
        Assert.assertNotNull(xhtmlNode);
        
        // html tag not null
        Assert.assertNotNull(xhtmlNode);
        
        // html tag is html
        Assert.assertEquals("html", xhtmlNode.toString());
        
    }
}
