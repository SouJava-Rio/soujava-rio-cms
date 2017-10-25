package br.org.soujava;


import static io.undertow.servlet.Servlets.listener;
import static io.undertow.servlet.Servlets.servlet;

import javax.servlet.ServletException;

import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

import br.org.soujava.integration.jersey.JerseyApp;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 * Classe Principal do CMS
 * 
 * @author arruda
 *
 */
public class CmsApplication extends Application {
	
	private static Undertow server;
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());

		Scene scene = new Scene(new Browser(stage));

		stage.setTitle("CMS - SouJava Site");
		stage.setScene(scene);
		stage.show();
	}


    public static void stopContainer(){
        server.stop();
    }

    public static void startContainer(int port) throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment();

        servletBuilder
                .setClassLoader(Application.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("cms-soujava-site.war")
                .addListeners(listener(Listener.class))
                .addServlets(servlet("jerseyServlet", ServletContainer.class)
                        .setLoadOnStartup(1)
                        .addInitParam("javax.ws.rs.Application", JerseyApp.class.getName())
                        .addMapping("/api/*"));

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        PathHandler path = Handlers.path(Handlers.redirect("/"))
                .addPrefixPath("/", manager.start());

        server =
                Undertow
                        .builder()
                        .addHttpListener(port, "localhost")
                        .setHandler(path)
                        .build();

        server.start();
    }
}

class Browser extends Region {
	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();
	final Stage stage;

	public Browser(Stage stage) {

		this.stage = stage;
		
		// load the web page
		webEngine.load(Browser.class.getResource("/WEB-VIEW/index.html").toExternalForm());

		// Update the stage title when a new web page title is available

		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			public void changed(ObservableValue<? extends State> ov, State oldState, State newState){

				if (newState == State.SUCCEEDED){

					// stage.setTitle(webEngine.getLocation());

					stage.setTitle(webEngine.getTitle());
				}
			}
		});

		// add the web view to the scene
		getChildren().add(browser);

	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}

	@Override
	protected double computePrefWidth(double height) {
		return 900;
	}

	@Override
	protected double computePrefHeight(double width) {
		return 600;
	}
}
