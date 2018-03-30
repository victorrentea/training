package com.crossover.trial.weather.webservice;

import java.util.List;
import java.util.Map;

import com.crossover.trial.weather.domain.Weather;

/**
 * The query API for the Weather Server Application. 
 * This API is made available to the public Internet.
 */
public interface RestWeatherQuery {

    /**
     * Retrieve health and status information for the the query api. Returns information about how the number
     * of datapoints currently held in memory, the frequency of requests for each IATA code and the frequency of
     * requests for each radius.
     */
    Map<String, Object> ping();

    /**
     * Retrieve the most up to date atmospheric information from the given airport and other airports in the given
     * radius.
     */
    List<Weather> get(String iataCode, double radiusKm);
}
