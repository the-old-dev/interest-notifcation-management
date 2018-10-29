package org.inm.subscription.filter;

import org.dizitart.no2.objects.ObjectFilter;

abstract class AbstractFilter {
    
    public enum TYPES {
        And, Equals, LessThen, LessThenEquals
    }
    
    private TYPES type;
    
    public void setType(TYPES type) {
        this.type = type;
    }
    
    public TYPES getType() {
        return this.type;
    }
    
    public abstract ObjectFilter create() ;
    
}
