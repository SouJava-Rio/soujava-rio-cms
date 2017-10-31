package br.org.soujava.integration.firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Firebase Database Realtime Client
 * 
 * @author Fernando Arruda - progfer@gmail.com
 *
 */
@Named
@ApplicationScoped
public class FirebaseDataBaseClient implements Serializable {

	private final FirebaseDatabase database;

	public FirebaseDataBaseClient() throws IOException {
		FileInputStream serviceAccount = new FileInputStream(
				"/Users/arruda/Documents/soujava-rio/token/soujava-rio-firebase-adminsdk-w0w8c-f9625b63c9.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
				.setDatabaseUrl("https://soujava-rio.firebaseio.com").build();

		FirebaseApp.initializeApp(options);

		database = FirebaseDatabase.getInstance();

	}

	public DatabaseReference getSlidesRepository() {
		DatabaseReference ref = database.getReference("slides");
		return ref;
	}

}