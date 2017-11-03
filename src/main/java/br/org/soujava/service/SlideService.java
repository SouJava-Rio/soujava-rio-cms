package br.org.soujava.service;
/**
 * Slide Service
 * 
 * @author Fernando Arruda - progfer@gmail.com
 *
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import br.org.soujava.domain.Slide;
import br.org.soujava.integration.firebase.FirebaseDataBaseClient;

/**
 * Slide Services
 * 
 * @author arruda
 *
 */
@Named
@ApplicationScoped
public class SlideService implements Serializable {

	@Inject
	private FirebaseDataBaseClient firebaseDataBaseClient;

	private Map<String, Slide> map = new HashMap<>();

	public SlideService() {
		this.map.put("AACDE221",
				Slide.builder().id("AACDE221").title("Title Slide 01").description("Slide 01 Desc").build());
	}

	public List<Slide> findAll() {
		final ResteasyWebTarget slideRestClient = firebaseDataBaseClient.getSlideRestClient();
		Response response = slideRestClient.request().get();
		try{
			return response.readEntity(new GenericType<List<Slide>>() {});		
		}finally{
			if(response != null){
				response.close();
			}
		}
	}

	public Slide findbyId(String id) {
		return map.get(id);
	}

	public void save(Slide slide) {
		map.put(slide.getId(), slide);
	}

	public void update(Slide slide) {
		map.put(slide.getId(), slide);
	}

	public void delete(String id) {
		map.remove(id);
	}

}
