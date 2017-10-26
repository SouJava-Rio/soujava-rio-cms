package br.org.soujava.producer;

import javax.enterprise.inject.Produces;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.soujava.api.provider.ObjectMapperFactory;
import br.org.soujava.integration.firebase.FirebaseDataBaseClient;
import br.org.soujava.service.SlideService;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class ApplicationProducer {
    
	@Produces
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.get();
    }

	@Produces
	public FirebaseDataBaseClient firebaseDataBaseClient(){
		return new FirebaseDataBaseClient();
	}
	
	@Produces
	public SlideService slideService(){
		return new SlideService();
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