package org.inm.util;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Json2ObjectReader {
    
    public static <T> T read(String resourceName, Class<T> objectClass ) {
        
		ObjectMapper objectMapper = new ObjectMapper();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		try {
		    
		    InputStream resourceStream = loader.getResourceAsStream(resourceName);
			return objectMapper.readValue(resourceStream, objectClass);
			
		} catch (JsonParseException e) {
			throw new RuntimeException("Can not parse the resource:=" + resourceName, e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Can not mapp to the handed object class, with the resource:=" + resourceName, e);
		} catch (IOException e) {
			throw new RuntimeException("IO Exception occured, while reading the resource:=" + resourceName, e);
		}
		
	}
    
}
