package org.inm.interest;

import org.codehaus.groovy.runtime.metaclass.MethodMetaProperty.GetBeanMethodMetaProperty;
import org.inm.interest.LocationService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class LocationFixer {
    
    @Configuration
    @ComponentScan("org.inm.interest")
    static class ApplicationConfiguration {}

	public static void main(String[] args) {
		
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        
        LocationStore store = context.getBean(LocationStore.class);
        LocationService service = context.getBean(LocationService.class);
        
		for (Location unlocated : store.findUnlocated()) {
			System.out.println(unlocated);
		}
		
		// store.insert(new Location("Garmisch-München",null, null));
		
		Location unlocated = store.findByIdField("St.Peter-Ording");
		System.out.println(unlocated);
		
		try {
    		store.delete(unlocated);
    		Location relocated = service.getLocation(unlocated.getName());
    		System.out.println(relocated+"\n");
    	} finally {
    
    		if (!store.exists(unlocated)) {
    			store.insert(unlocated);
    		}
    		
    		
    	}

	}

}
