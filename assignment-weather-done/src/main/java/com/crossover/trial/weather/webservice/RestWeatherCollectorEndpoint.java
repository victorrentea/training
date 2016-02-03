package com.crossover.trial.weather.webservice;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.WeatherException;
import com.crossover.trial.weather.domain.Airport;
import com.crossover.trial.weather.domain.DataPoint;
import com.crossover.trial.weather.domain.DataPointType;
import com.crossover.trial.weather.service.WeatherService;

/**
 * A REST implementation of the WeatherCollector API. Accessible only to airport weather collection
 * sites via secure VPN.
 */
@Path("/collect")
@Produces(MediaType.APPLICATION_JSON)
public class RestWeatherCollectorEndpoint implements RestWeatherCollector {

    @Inject
    private WeatherService weatherService;
    
    @GET
    @Path("/ping")
    @Override
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() {
        return Response.ok("ready").build();
    }

    @POST
    @Path("/weather/{iata}/{pointType}")
    @Override
    public Response updateWeather(@PathParam("iata") String iataCode,
                                  @PathParam("pointType") String pointTypeStr,
                                  DataPoint datapoint) {
    	
    	DataPointType pointType = DataPointType.valueOf(pointTypeStr.toUpperCase());
    	if (pointType == null) {
    		throw new WeatherException("Invalid point type: " + pointTypeStr);
    	}
    	weatherService.setDataPoint(iataCode, pointType, datapoint);
    	return Response.ok().build();
    }

    @GET
    @Path("/airports")
    @Override
    public Response getAirportsIataCodes() {
        Set<String> iataCodeList = weatherService.getAllIataCodes();
        return Response.ok(iataCodeList).build();
    }

    @GET
    @Path("/airport/{iata}")
    @Override
    public Response getAirport(@PathParam("iata") String iataCode) {
        Airport airport = weatherService.findAirport(iataCode);
        return Response.ok(airport).build();
    }

    @POST
    @Path("/airport/{iata}/{lat}/{long}")
    @Override
    public Response addAirport(@PathParam("iata") String iataCode,
                               @PathParam("lat") double latitude,
                               @PathParam("long") double longitude) {
        weatherService.createAirport(iataCode, latitude, longitude);
        return Response.ok().build();
    }
    
    @POST
    @Path("/airport")
    @Override
    public Response addAirport(Airport airport) {
        weatherService.createAirport(airport);
        return Response.ok().build();
    }

    @DELETE
    @Path("/airport/{iata}")
    @Override
    public Response deleteAirport(@PathParam("iata") String iataCode) {
    	weatherService.deleteAirport(iataCode);
        return Response.ok().build();
    }
    
    @Path("/reset")
    @GET
    public void _technical_resetData() {
    	weatherService.resetData();
    }

   
}
