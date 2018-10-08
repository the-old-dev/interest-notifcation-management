package io.openshift.booster.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;

@RestController
public class GreetingEndpoint {

    private final String template;
    
    private Nitrite db;
    private ObjectRepository<Greeting> repository;
    
    Logger logger = LoggerFactory.getLogger(GreetingEndpoint.class);
    
    public GreetingEndpoint() {
        template = "Hello, %s!";
        initialize();
    }

    @RequestMapping("/api/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        
        String message = String.format(template, name);
        
        Greeting greeting = null;
        
        try{
            greeting = new Greeting(message + "; Time:="+System.currentTimeMillis());
            repository.insert(greeting);
            logger.info("NitriteDB: Repository insert happend");
        } catch (Exception e) {
            logger.error(e.toString());
            greeting = new Greeting(message+ "; Errors:=" + e.toString());
        } 
        
        return greeting;
    }
    
    @RequestMapping("/api/greetings")
    public Cursor<Greeting> greetings() {
        return  repository.find();
    }
    
    private void initialize() {
        
        logger.info("NitriteDB: starting ...");
        
        try {
            
            // Initialize DB
            db = Nitrite.builder()
                .compressed()
                .filePath("test.db")
                .openOrCreate("user", "password");
                
            logger.info("NitriteDB: started -" + db);
                   
            // Initialize an Object Repository
            repository = db.getRepository(Greeting.class);
            logger.info("NitriteDB: Repository created");
            
        } catch (Exception e) {
            logger.error(e.toString());
        }    

    }
}
