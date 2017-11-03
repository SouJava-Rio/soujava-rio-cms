package br.org.soujava.integration.firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import br.org.soujava.config.Property;

/**
 * Firebase Database Realtime Client
 * 
 * @author Fernando Arruda - progfer@gmail.com
 *
 */
@Named
@Singleton
public class FirebaseDataBaseClient implements Serializable {

	private String googleOAuthToken;
	private ResteasyWebTarget slideRestClient;

	@Inject
	@Property("firebase.account.key.file")
	private String firebaseAccountKeyFile;

	@Inject
	@Property("firebase.url.ws.slides")
	private String firebaseUrlWsSlides;

	public String getGoogleOAuthToken() {
		return googleOAuthToken;
	}

	public ResteasyWebTarget getSlideRestClient() {
		return slideRestClient;
	}

	@PostConstruct
	public void init() throws IOException {
		// Load the service account key JSON file
		FileInputStream serviceAccount = new FileInputStream(firebaseAccountKeyFile);

		// Authenticate a Google credential with the service account
		GoogleCredential googleCred = GoogleCredential.fromStream(serviceAccount);

		// Add the required scopes to the Google credential
		GoogleCredential scoped = googleCred.createScoped(Arrays.asList(
				"https://www.googleapis.com/auth/firebase.database", 
				"https://www.googleapis.com/auth/userinfo.email"));

		// Use the Google credential to generate an access token
		scoped.refreshToken();
		
		googleOAuthToken = scoped.getAccessToken();

		// Create Slide Database client
		ResteasyClient client = new ResteasyClientBuilder().build();
		
		slideRestClient = client.target(firebaseUrlWsSlides);
	}

}