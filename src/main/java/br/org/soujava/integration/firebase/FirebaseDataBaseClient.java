package br.org.soujava.integration.firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import javax.inject.Named;
import javax.inject.Singleton;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

/**
 * Firebase Database Realtime Client
 * 
 * @author Fernando Arruda - progfer@gmail.com
 *
 */
@Named
@Singleton
public class FirebaseDataBaseClient implements Serializable {

	private final String googleOAuthToken;
    private final ResteasyWebTarget slideRestClient; 

	public FirebaseDataBaseClient() throws IOException {
		
		// Load the service account key JSON file
		FileInputStream serviceAccount = new FileInputStream(
				"/Users/arruda/Downloads/soujava-rio-firebase-adminsdk-w0w8c-f9625b63c9.json");

		// Authenticate a Google credential with the service account
		GoogleCredential googleCred = GoogleCredential.fromStream(serviceAccount);

		// Add the required scopes to the Google credential
		GoogleCredential scoped = googleCred.createScoped(
		    Arrays.asList(
		      "https://www.googleapis.com/auth/firebase.database",
		      "https://www.googleapis.com/auth/userinfo.email"
		    )
		);

		// Use the Google credential to generate an access token
		scoped.refreshToken();
		googleOAuthToken = scoped.getAccessToken();
		
		//Create Slide Database client
		ResteasyClient client = new ResteasyClientBuilder().build();
		slideRestClient = client.target("https://soujava-rio.firebaseio.com/site/slides.json");
	}

	public String getGoogleOAuthToken() {
		return googleOAuthToken;
	}

	public ResteasyWebTarget getSlideRestClient() {
		return slideRestClient;
	}
	
}