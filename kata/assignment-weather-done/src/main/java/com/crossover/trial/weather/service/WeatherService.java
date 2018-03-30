package com.crossover.trial.weather.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crossover.trial.weather.WeatherException;
import com.crossover.trial.weather.domain.Airport;
import com.crossover.trial.weather.domain.DataPoint;
import com.crossover.trial.weather.domain.DataPointType;
import com.crossover.trial.weather.domain.Weather;

public class WeatherService {

	private final static Logger log = LoggerFactory.getLogger(WeatherService.class);
	
	private static final String INITIAL_AIRPORTS_RESOURCE_NAME = "airports.dat";

	private static final double EARTH_RADIUS = 6372.8;

	@Inject
	private WeatherRepository repository;
	
	@PostConstruct
    public void loadDefaultAirports() {
		log.debug("Loading initial airports");
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(INITIAL_AIRPORTS_RESOURCE_NAME)) {
			if (is == null) {
				throw new WeatherException("Could not load initial airports: Could not open classpath resource: " + INITIAL_AIRPORTS_RESOURCE_NAME);
			}
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
	        	String line; 
        		while ( (line = br.readLine()) != null) {
        			String[] split = line.split(",");
        			createAirport(split[0], Double.valueOf(split[1]), Double.valueOf(split[2]));
        		}
	        }
		} catch (IOException e) {
			throw new WeatherException(e);
		}
    }
	
	public void resetData() {
		repository.deleteAll();
		loadDefaultAirports();
	}
	
    public void setDataPoint(String iataCode, DataPointType type, DataPoint dataPoint)  {
    	Airport airport = repository.getAirport(iataCode);
        Weather weather = airport.getWeather();
        if (! type.isValid(dataPoint.getMean())) {
			throw new WeatherException("Illegal mean value for data point type " + type + ": " + dataPoint.getMean());
		}
		switch (type) {
		case WIND: weather.setWind(dataPoint); break;
		case TEMPERATURE: weather.setTemperature(dataPoint); break;
		case HUMIDTY: weather.setHumidity(dataPoint); break;
		case PRESSURE: weather.setPressure(dataPoint); break;
		case CLOUDCOVER: weather.setCloudCover(dataPoint); break;
		case PRECIPITATION: weather.setPrecipitation(dataPoint); break;
		default: throw new WeatherException("Not implemented");
		}
		weather.setLastUpdateTime(System.currentTimeMillis());
		log.debug("Successfully updated " + type + " for airport " + iataCode);
	}

    public Airport createAirport(String iataCode, double latitude, double longitude) {
        Airport airport = new Airport(iataCode, latitude, longitude);
        repository.saveAirport(airport);
        return airport;
    }
    
    
    public List<Weather> searchAirportWeather(String iataCode, double radiusKm) {
    	Airport centralAirport = repository.getAirport(iataCode);
        if (radiusKm < 0) {
        	throw new WeatherException("Radius cannot be negative");
        }
        if (radiusKm < .0001) {
        	return Collections.singletonList(centralAirport.getWeather());
        } 
    	List<Weather> resultList = new ArrayList<>();
        for (Airport otherAirport : repository.getAllAirports()){
            if (centralAirport == otherAirport ||
            	calculateHaversineDistance(centralAirport, otherAirport) <= radiusKm){
                
            	Weather weather = otherAirport.getWeather();
                if (!weather.isEmpty()) {
                    resultList.add(weather);
                }
            }
        }
        return resultList;
	}

    public Airport findAirport(String iataCode) {
    	return repository.findAirport(iataCode);
    }

    /** Computes the Haversine distance in KM */
    public double calculateHaversineDistance(Airport a1, Airport a2) {
        double deltaLat = Math.toRadians(a2.getLatitude() - a1.getLatitude());
        double deltaLon = Math.toRadians(a2.getLongitude() - a1.getLongitude());
        double a =  Math.pow(Math.sin(deltaLat / 2), 2) + Math.pow(Math.sin(deltaLon / 2), 2)
                * Math.cos(a1.getLatitude()) * Math.cos(a2.getLatitude());
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;
    }

	public Set<String> getAllIataCodes() {
		Set<String> resultList = new HashSet<>();
		for (Airport airport : repository.getAllAirports()) {
			resultList.add(airport.getIata());
		}
		return resultList;
	}

	public void deleteAirport(String iataCode) {
		repository.removeAirport(iataCode);
	}

	public void createAirport(Airport airport) {
		repository.saveAirport(airport);
	}
	
}
