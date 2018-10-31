package org.inm.website;

import org.inm.website.AbstractChangeDetector;
import org.inm.interest.Interest;

public class DummyChangeDetector extends AbstractChangeDetector {
    
    protected boolean stop(Interest readed, Interest existing) {
        return true;
    }

}
