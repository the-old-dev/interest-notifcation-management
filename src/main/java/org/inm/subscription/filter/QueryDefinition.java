package org.inm.subscription.filter;

import java.util.List;

public class QueryDefinition {
    
    private List<QueryFilter> filters;
    
    public void setFilters(List<QueryFilter> filters) {
        this.filters = filters;
    }
    
    public List<QueryFilter> getFilters() {
        return filters;
    }
    
}