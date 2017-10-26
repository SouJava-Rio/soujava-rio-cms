package br.org.soujava.service;
/**
 * Slide Service
 * 
 * @author Fernando Arruda - progfer@gmail.com
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.org.soujava.domain.Slide;
import br.org.soujava.integration.firebase.FirebaseDataBaseClient;

/**
 * Slide Services
 * 
 * @author arruda
 *
 */
public class SlideService {

	@Inject
	private FirebaseDataBaseClient firebaseDataBaseClient;
	
	private Map<String, Slide> map = new HashMap<>();
	
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
