package com.crossover.trial.weather;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AirportLoaderTest {
	
	private static final int port = 8080;
	private static final String BASE_URI = "http://localhost:"+port;
	public static final String[] IMPORTED_IATA_CODES = new String[] { "BOS", "EWR", "JFK", "LCY", "LGA", "LHR", "LTN",
			"LPL", "MMU", "STN" };
	
	private Client client = ClientBuilder.newClient();
	private WebTarget collect = client.target(BASE_URI + "/collect");

	private AirportLoader target = new AirportLoader(BASE_URI);

    @BeforeClass
    public static void startServer() throws IOException {
    	WeatherServer.start(port);
    }

    @AfterClass
    public static void stopServer() {
    	WeatherServer.stop();
    }
	
	@Test
	public void testTokenize() {
		assertEquals(Arrays.asList("a","b","-2"), target.tokenize("\"a\",b,-2"));
		assertEquals(Arrays.asList("a,b","-2"), target.tokenize("\"a,b\",-2"));
	}
	
	@Test
	public void endToEndTest() throws IOException {
		target.upload(AirportLoaderTest.class.getResourceAsStream("/airports-test.txt"));
		for (String iata:IMPORTED_IATA_CODES) {
			Response response = collect.path("/airport/"+iata).request().get();
			assertEquals(200, response.getStatus());
		}
	}
			

}
