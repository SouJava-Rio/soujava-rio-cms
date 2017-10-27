package br.org.soujava.integration.resteasy;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.org.soujava.api.endpoint.SlideEndpoint;
import br.org.soujava.integration.firebase.FirebaseDataBaseClient;
import br.org.soujava.service.SlideService;

@ApplicationPath("/api")
public class RestEasyApplication extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	
	
	public RestEasyApplication() {
//		singletons.add(new FirebaseDataBaseClient());
//		singletons.add(new SlideService());
	}
	
    @Override
    public Set<Class<?>> getClasses() {
    	
        Set<Class<?>> classes = new HashSet<>();
        classes.add(FirebaseDataBaseClient.class);
        classes.add(SlideService.class);
        classes.add(SlideEndpoint.class);
        classes.add(RestEasyDefaultCharsetInterceptor.class);
        
        return classes;
    }
     
    @Override
    public Set<Object> getSingletons() {
    	return singletons;
    }
}
