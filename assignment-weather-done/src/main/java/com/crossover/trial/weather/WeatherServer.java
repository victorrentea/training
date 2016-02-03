package com.crossover.trial.weather;

import static java.lang.String.format;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpServerFilter;
import org.glassfish.grizzly.http.server.HttpServerProbe;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.crossover.trial.weather.service.AuditService;
import com.crossover.trial.weather.service.WeatherRepository;
import com.crossover.trial.weather.service.WeatherService;
import com.crossover.trial.weather.webservice.GenericExceptionMapper;
import com.crossover.trial.weather.webservice.RestWeatherCollectorEndpoint;
import com.crossover.trial.weather.webservice.RestWeatherQueryEndpoint;

/**
 * A main method used to test the Weather Application locally -- live deployment is to a tomcat container.
 *
 * @author code test administrator
 */
public class WeatherServer {

	private static HttpServer server;

	public static void start(int port) throws IOException {
		String baseUrl = "http://localhost:"+port+"/";
		System.out.println("Starting Weather App local testing server: " + baseUrl);
		System.out.println("Not for production use");

		final ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.register(RestWeatherCollectorEndpoint.class);
		resourceConfig.register(RestWeatherQueryEndpoint.class);
		resourceConfig.register(GenericExceptionMapper.class);
		resourceConfig.register(new MyApplicationBinder());
		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUrl), resourceConfig, false);

		HttpServerProbe probe = new HttpServerProbe.Adapter() {
			@Override
			public void onRequestReceiveEvent(HttpServerFilter filter, @SuppressWarnings("rawtypes") Connection connection, Request request) {
				System.out.println(request.getRequestURI());
			}
		};

		server.getServerConfiguration().getMonitoringConfig().getWebServerConfig().addProbes(probe);
		System.out.println(format("Weather Server started.\n url=%s\n", baseUrl));
		server.start();
	}

	public static void stop() {
		server.shutdownNow();
	}

	public static void main(String[] args) {
		try {
			start(8080);
			System.out.println("After start");
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					if (server != null)
						stop();
				}
			}));
			Thread.currentThread().join();

		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(WeatherServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static class MyApplicationBinder extends AbstractBinder {
		@Override
		protected void configure() {
			bind(WeatherService.class).to(WeatherService.class).in(Singleton.class);
			bind(AuditService.class).to(AuditService.class).in(Singleton.class);
			bind(WeatherRepository.class).to(WeatherRepository.class).in(Singleton.class);
		}
	}
}
