package org.inm.subscription.filter;

import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.filters.ObjectFilters;

abstract class FieldFilter extends AbstractFilter {
        
    private String field;
    private Object value;
    
    public void setField(String field) {
        this.field = field;
    }
    
    public String getField() {
        return this.field;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    public Object getValue() {
        return this.field;
    }
    
    protected ObjectFilter create(TYPES type) {
    
        switch(type) {
            
            case Equals:    
                return ObjectFilters.eq(getField(),getValue());
                      
            case LessThen:    
                return ObjectFilters.lt(getField(),getValue());
                
            case LessThenEquals:    
                return ObjectFilters.lte(getField(),getValue());
                
            default:        
                return null;
        
        }
        
    }
    
}