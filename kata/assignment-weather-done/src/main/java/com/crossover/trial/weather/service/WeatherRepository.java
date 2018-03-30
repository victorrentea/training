package com.crossover.trial.weather.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crossover.trial.weather.WeatherException;
import com.crossover.trial.weather.domain.Airport;

public class WeatherRepository {
	
	private final static Logger log = LoggerFactory.getLogger(WeatherRepository.class);
	 
    private Map<String, Airport> airports = new HashMap<>();

    public Airport findAirport(String iataCode) {
    	return airports.get(iataCode);
    }

    public Airport getAirport(String iataCode) {
    	if (! airports.containsKey(iataCode)) {
    		throw new WeatherException("Airport not found for iata code: " + iataCode);
    	}
    	return airports.get(iataCode);
    }

	public void saveAirport(Airport airport) {
		Airport existing = airports.get(airport.getIata());
		if (existing != null) {
			log.warn("Overwriting airport data for iata code: '" + airport.getIata() + "'!");
			airport.setWeather(existing.getWeather());
		}
		airports.put(airport.getIata(), airport);
	}

	public Collection<Airport> getAllAirports() {
		return airports.values();
	}

	public void deleteAll() {
		airports.clear();		
	}

	public void removeAirport(String iataCode) {
		boolean removed = airports.remove(iataCode) != null;
		if (!removed) {
			throw new WeatherException("Airport not found for iataCode: " + iataCode); 
		}
	}
    
}
