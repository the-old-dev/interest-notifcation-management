package io.openshift.booster.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// Nitrite B
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.inm.website.dailydose.DailyDoseOffersReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RestController
public class GreetingEndpoint {

    private final String template;
    
    private Nitrite db;
    private ObjectRepository<Greeting> repository;
    
    Logger logger = LoggerFactory.getLogger(GreetingEndpoint.class);
    
    public GreetingEndpoint() {
        template = "Hello, %s!";
        initialize();
    }
    
    @RequestMapping("/api/secret")
    public String secret(@RequestParam(value="name", defaultValue="my-secret") String name) {
        try {
            return "env:="+System.getenv(name)+"; prop:="+System.getProperty(name, "not available");
        } catch (Exception e) {
            return "error:="+e.toString();
        }

    }

    @RequestMapping("/api/envs")
    public Map<String,String> envs() {
        try {
            return System.getenv();
        } catch (Exception e) {
            Map<String,String> map = new HashMap <String,String>();
            map.put("error",e.toString());
            return map;
        }
    }
    
    @RequestMapping("/api/props")
    public Properties props() {
        try {
            return System.getProperties();
        } catch (Exception e) {
            Properties map = new Properties();
            map.put("error",e.toString());
            return map;
        }
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
    
    @RequestMapping("/api/dailydose")
    public String dailyDose() throws Exception {
        
        try {
        
            // create instances
            DailyDoseOffersReader reader = new DailyDoseOffersReader();
        
            // initialise instances
            boolean implemented  = false;
            if (implemented == false) {
            	throw new RuntimeException("Not yet implemented");
            }
        
            // run & return
            return "First tag:=" + reader.getActualDataAsXml().toString();
        
        } catch (Exception e) {
            
            logger.error(e.toString());
            return "Error:=" + e.toString();
        
        }  
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
