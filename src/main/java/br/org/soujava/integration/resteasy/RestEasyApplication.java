package br.org.soujava.integration.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/*")
public class JerseyApp extends ResourceConfig {
    public JerseyApp() {
        packages(true, "br.org.soujava");
    }
}
