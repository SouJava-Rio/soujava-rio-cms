package br.org.soujava;

import static io.undertow.Handlers.resource;

import java.time.Duration;

import javax.servlet.ServletException;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import br.org.soujava.integration.resteasy.RestEasyApplication;
import de.codecentric.centerdevice.MenuToolkit;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
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

	private static UndertowJaxrsServer server;
	static final String appName = "CMS - SouJava Site";

	public static void main(String[] args) throws Exception {
		startContainer(8080);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		primaryStage.initStyle(StageStyle.UNDECORATED);
		// Get the toolkit
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		primaryStage.setTitle("CMS - SouJava Site");

		Scene scene = new Scene(new Browser(primaryStage));

		primaryStage.setScene(scene);
		primaryStage.requestFocus();
		primaryStage.show();
		MenuToolkit tk = MenuToolkit.toolkit();


		// Application Menu
		// TBD: services menu
		Menu appMenu = new Menu(appName); // Name for appMenu can't be set at
											// Runtime
		MenuItem aboutItem = tk.createAboutMenuItem(appName);

		appMenu.getItems().addAll(aboutItem, new SeparatorMenuItem(),new SeparatorMenuItem(),
				tk.createHideMenuItem(appName), tk.createHideOthersMenuItem(), tk.createUnhideAllMenuItem(),
				new SeparatorMenuItem(), tk.createQuitMenuItem(appName));
		
		// Update the existing Application menu
		tk.setApplicationMenu(appMenu);

	}

	@Override
	public void stop() throws Exception {
		server.stop();
	}

	public static void startContainer(int port) throws ServletException {

		server = new UndertowJaxrsServer();

		ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setApplicationClass(RestEasyApplication.class.getName());
		deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");

		DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/");
		deploymentInfo.setClassLoader(CmsApplication.class.getClassLoader());
		deploymentInfo.setDeploymentName("Undertow + Resteasy example");
		deploymentInfo.setContextPath("/api");

		deploymentInfo.addListener(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

		server.deploy(deploymentInfo);

		final PathHandler handler = new PathHandler();
		handler.addPrefixPath("/WEB-VIEW", createStaticResourceHandler());

		server.addResourcePrefixPath("/", resource(new ClassPathResourceManager(CmsApplication.class.getClassLoader()))
				.addWelcomeFiles("index.html"));

		Undertow.Builder builder = Undertow.builder().setHandler(handler).addHttpListener(8080, "localhost");

		server.start(builder);

	}

	private static HttpHandler createStaticResourceHandler() {
		final ResourceManager staticResources = new ClassPathResourceManager(CmsApplication.class.getClassLoader(),
				"WEB-VIEW");
		// Cache tuning is copied from Undertow unit tests.
		final ResourceManager cachedResources = new CachingResourceManager(100, 65536,
				new DirectBufferCache(1024, 10, 10480), staticResources, (int) Duration.ofDays(1).getSeconds());
		final ResourceHandler resourceHandler = new ResourceHandler(cachedResources);
		resourceHandler.setWelcomeFiles("index.html");
		return resourceHandler;
	}
}

class Browser extends Region {
	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();
	final Stage stage;

	public Browser(Stage stage) {
		this.stage = stage;

		// load the web page
		// webEngine.load(Browser.class.getResource("/index.html").toExternalForm());

		webEngine.load("http://localhost:8080/WEB-VIEW/");

		// Update the stage title when a new web page title is available

		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {

				if (newState == State.SUCCEEDED) {

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
