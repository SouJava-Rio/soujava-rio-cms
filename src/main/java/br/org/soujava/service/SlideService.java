package br.org.soujava.service;
/**
 * Slide Service
 * 
 * @author Fernando Arruda - progfer@gmail.com
 *
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
public class SlideService implements Serializable{

	@Inject
	private FirebaseDataBaseClient firebaseDataBaseClient;
	
	private Map<String, Slide> map = new HashMap<>();
	
	public SlideService() {
		this.map.put("AACDE221", Slide.builder().id("AACDE221").title("Title Slide 01").description("Slide 01 Desc").build());
	}
	
	public List<Slide> findAll(){
		return new ArrayList<>(map.values());
	}
	
	public Slide findbyId(String id){
		return map.get(id);
	}
	
	public void save(Slide slide){
		map.put(slide.getId(), slide);
	}
	
	public void update(Slide slide){
		map.put(slide.getId(), slide);
	}
	
	public void delete(String id){
		map.remove(id);
	}
	
}
