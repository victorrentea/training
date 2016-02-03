package com.crossover.trial.weather;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.crossover.trial.weather.domain.Airport;
import com.crossover.trial.weather.domain.DataPoint;
import com.crossover.trial.weather.domain.Weather;

public class WeatherEndpointTest {

	private static final int port = 8080;
	private static final String BASE_URI = "http://localhost:"+port;
	
    @BeforeClass
    public static void startServer() throws IOException {
    	WeatherServer.start(port);
    }

    @AfterClass
    public static void stopServer() {
    	WeatherServer.stop();
    }

    private Client client = ClientBuilder.newClient();
    private WebTarget query = client.target(BASE_URI + "/query");
    private WebTarget collect = client.target(BASE_URI + "/collect");
    
    private DataPoint windDataPoint = new DataPoint.Builder()
            .withFirst(0)
            .withSecond(4)
            .withThird(10)
            .withMean(4)
            .withCount(10)
            .build();
    
    @Before
    public void setUp() throws Exception {
    	collect.path("/reset").request().get();
    }
    
    @Test
    public void pingCollect() {
        Response response = collect.path("/ping").request().get();
        String responseText = response.readEntity(String.class);
		assertEquals("ready", responseText);
    }

    @Test
    public void pingQuery() {
        Response response = query.path("/ping").request().get();
        assertEquals(200, response.getStatus());
        System.out.println("query.ping: " + response.readEntity(String.class));
    }
    
    @Test
    public void setAndGetDataPoint() {
    	Response response = query.path("/weather/BOS/0").request().get();
        assertNull(response.readEntity(new GenericType<List<Weather>>(){})
        		.get(0).getWind());
    	
        collect.path("/weather/BOS/wind").request()
        		.post(Entity.entity(windDataPoint, "application/json"));
        
        response = query.path("/weather/BOS/0").request().get();
        List<Weather> fromServer = response.readEntity(new GenericType<List<Weather>>(){});
        
        assertEquals(windDataPoint, fromServer.get(0).getWind());
    }
    
    @Test
    public void setInvalidDataPointTypeFails() {
        Response response = collect.path("/weather/BOS/xyz").request()
        		.post(Entity.entity(windDataPoint, "application/json"));
        assertEquals(500, response.getStatus());
    }

    @Test
    public void searchWeatherWithRadius() {
    	collect.path("/weather/JFK/wind").request().post(Entity.entity(windDataPoint, "application/json"));
    	collect.path("/weather/EWR/wind").request().post(Entity.entity(windDataPoint, "application/json"));
    	collect.path("/weather/LGA/wind").request().post(Entity.entity(windDataPoint, "application/json"));
    	
    	Response response = query.path("/weather/JFK/200").request().get();
        GenericType<List<Weather>> weatherListType = new GenericType<List<Weather>>(){};
        List<Weather> fromServer = response.readEntity(weatherListType);
        assertEquals(3, fromServer.size());
    }
    
    @Test
    public void getAirport() {
    	Response response = collect.path("/airport/BOS").request().get();
    	Airport airport = response.readEntity(Airport.class);
    	System.out.println(airport);
    	assertEquals(42.364347, airport.getLatitude(), .00000001); 
    	assertEquals(-71.005181, airport.getLongitude(), .00000001);
    }

    @Test
    public void getAllAirportIataCodes() {
    	Response response = collect.path("/airports").request().get();
    	List<String> airportCodes = response.readEntity(new GenericType<List<String>>(){});
    	assertThat(airportCodes, hasItem("BOS"));
    	assertEquals(5, airportCodes.size());
    }

}