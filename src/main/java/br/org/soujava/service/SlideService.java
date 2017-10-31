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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.org.soujava.domain.Slide;
import br.org.soujava.integration.firebase.FirebaseDataBaseClient;
import lombok.extern.slf4j.Slf4j;

/**
 * Slide Services
 * 
 * @author arruda
 *
 */
@Named
@ApplicationScoped
@Slf4j
public class SlideService implements Serializable {

	@Inject
	private FirebaseDataBaseClient firebaseDataBaseClient;

	private Map<String, Slide> map = new HashMap<>();

	public SlideService() {
		this.map.put("AACDE221",
				Slide.builder().id("AACDE221").title("Title Slide 01").description("Slide 01 Desc").build());
	}

	public List<Slide> findAll() {
		DatabaseReference slidesRepository = firebaseDataBaseClient.getSlidesRepository();
		// Attach a listener to read the data at our posts reference
		slidesRepository.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				//Post post = dataSnapshot.getValue(Post.class);
				System.out.println(dataSnapshot.getValue());
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.out.println("The read failed: " + databaseError.getCode());
			}
		});
		return new ArrayList<>(map.values());
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
