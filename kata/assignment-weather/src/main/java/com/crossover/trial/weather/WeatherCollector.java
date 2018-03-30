package com.crossover.trial.weather;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * The interface shared to airport weather collection systems.
 *
 * @author code test administartor
 */
public interface WeatherCollector {
    /**
     * A liveliness check for the collection endpoint.
     *
     * @return 1 if the endpoint is alive functioning, 0 otherwise
     */
    Response ping();

    /**
     * Update the airports atmospheric information for a particular pointType with
     * json formatted data point information.
     *
     * @param iataCode the 3 letter airport code
     * @param pointType the point type, {@link DataPointType} for a complete list
     * @param datapointJson a json dict containing mean, first, second, thrid and count keys
     *
     * @return HTTP Response code
     */
    Response updateWeather(@PathParam("iata") String iataCode,
                           @PathParam("pointType") String pointType,
                           String datapointJson);

    /**
     * Return a list of known airports as a json formatted list
     *
     * @return HTTP Response code and a json formatted list of IATA codes
     */
    Response getAirports();

    /**
     * Retrieve airport data, including latitude and longitude for a particular airport
     *
     * @param iata the 3 letter airport code
     * @return an HTTP Response with a json representation of {@link AirportData}
     */
    Response getAirport(@PathParam("iata") String iata);

    /**
     * Add a new airport to the known airport list.
     *
     * @param iata the 3 letter airport code of the new airport
     * @param latString the airport's latitude in degrees as a string [-90, 90]
     * @param longString the airport's longitude in degrees as a string [-180, 180]
     * @return HTTP Response code for the add operation
     */
    Response addAirport(@PathParam("iata") String iata,
                        @PathParam("lat") String latString,
                        @PathParam("long") String longString);

    /**
     * Remove an airport from the known airport list
     *
     * @param iata the 3 letter airport code
     * @return HTTP Repsonse code for the delete operation
     */
    Response deleteAirport(@PathParam("iata") String iata);
}
