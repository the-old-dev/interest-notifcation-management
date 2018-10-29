package org.inm.subscription;

import java.util.ArrayList;
import java.util.List;

import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.filters.ObjectFilters;


public class FilterDefinition extends AbstractFieldFilterDefinition {
    
    private List<FilterDefinition> contained; 
    
    public List<FilterDefinition> getContained() {
        return contained;
    }
    
    public void setContained(List<FilterDefinition> contained) {
        this.contained = contained;
    }
    
        
    public ObjectFilter create() {
        if(TYPES.And.equals(this.getType())) {
        	
        	List<ObjectFilter> filters = new ArrayList<ObjectFilter>();
            
            for (FilterDefinition definition : getContained()) {
            	filters.add(definition.create());
            }
            
			return ObjectFilters.and(filters.toArray(new ObjectFilter[filters.size()]));
        } else {
            return create(getType());
        }
    }
    
}