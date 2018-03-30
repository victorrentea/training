package com.crossover.trial.weather.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.crossover.trial.weather.domain.AuditReport;
import com.crossover.trial.weather.domain.Weather;
import com.crossover.trial.weather.service.AuditService;
import com.crossover.trial.weather.service.WeatherService;

@Path("/query")
@Produces(MediaType.APPLICATION_JSON)
public class RestWeatherQueryEndpoint implements RestWeatherQuery {
    
    @Inject
    private AuditService auditService;
    
    @Inject
    private WeatherService weatherService;
   
    @Override
	@GET
    @Path("/ping")
    public Map<String, Object> ping() {
        AuditReport report = auditService.getReport();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("datasize", report.getDatasize());
        resultMap.put("iata_freq", report.getAirportFrequency());
        resultMap.put("radius_freq", report.getRadiusFrequency());
        return resultMap;
    }

    @Override
	@GET
    @Path("/weather/{iata}/{radius}")
    public List<Weather> get(@PathParam("iata") String iataCode, @PathParam("radius") double radiusKm) {
        List<Weather> result = weatherService.searchAirportWeather(iataCode, radiusKm);
        auditService.auditWeatherSearch(iataCode, radiusKm);
		return result;
    }

	
}
