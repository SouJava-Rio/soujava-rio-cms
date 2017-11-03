package br.org.soujava.integration.firebase;

import java.io.IOException;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;

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
	
	@Inject
	@Property("proxy.config.host")
	private String proxyHost;
	
	@Inject
	@Property("proxy.config.port")
	private Integer proxyPort;

	@Inject
	@Property("proxy.credential.username")
	private String proxyUsername;
	
	@Inject
	@Property("proxy.credential.password")
	private String proxyPassword;

	@Inject
	@Property("proxy.enabled")
	private boolean proxyEnabled;
	
	public String getGoogleOAuthToken() {
		return googleOAuthToken;
	}

	public ResteasyWebTarget getSlideRestClient() {
		return slideRestClient;
	}

	@PostConstruct
	public void init() throws IOException {

		ResteasyClient client = null;
		
		if(proxyEnabled) {
			Credentials credentials = new UsernamePasswordCredentials(proxyUsername, proxyPassword);
			
			CredentialsProvider credProvider = new BasicCredentialsProvider();
			credProvider.setCredentials(new AuthScope(proxyHost, proxyPort), credentials);
			
			HttpClient httpClient = HttpClientBuilder.create()
					.setProxy(new HttpHost(proxyHost, proxyPort))
					.setDefaultCredentialsProvider(credProvider)
					.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
					.build();
			
			ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);
			
			// Create Slide Database client 
			client = new ResteasyClientBuilder()
					.httpEngine(engine)
					.build();
		}else {
			// Create Slide Database client 
			client = new ResteasyClientBuilder().build();
		}
	    
		slideRestClient = client.target(firebaseUrlWsSlides);
	}

}