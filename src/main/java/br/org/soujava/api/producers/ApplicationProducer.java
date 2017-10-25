package br.org.soujava.api.producers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.glassfish.jersey.internal.util.Property;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.soujava.integration.jersey.ObjectMapperFactory;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class ApplicationProducer {
    
	@Produces
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.get();
    }


//    @Produces
//    @Property(value = "")
//    public String property(@InjectableProperties Map<String, String> properties, InjectionPoint injectionPoint){
//        final Property property = injectionPoint.getAnnotated().getAnnotation(Property.class);
//        return properties.get(property.value());
//    }
//
//    @ApplicationScoped
//    @Produces
//    @InjectableProperties
//    public Map<String, String> properties() throws IOException {
//        Map<String, String> map = new HashMap<>();
//        final ResourceBundle bundle = ResourceBundle.getBundle("application");
//        bundle.keySet().forEach(key -> map.put(key, bundle.getString(key)));
//        return map;
//    }
}