package org.inm.subscription.filter;

import java.util.List;

import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.filters.ObjectFilters;


public class QueryFilter extends FieldFilter {
    
    private List<QueryFilter> contained; 
    
    public List<QueryFilter> getContained() {
        return contained;
    }
    
    public void setContained(List<QueryFilter> contained) {
        this.contained = contained;
    }
    
        
    public ObjectFilter create() {
        if(TYPES.And.equals(this.getType())) {
            return ObjectFilters.and(getContained().toArray(new ObjectFilter[getContained().size()]));
        } else {
            return create(getType());
        }
    }
    
}