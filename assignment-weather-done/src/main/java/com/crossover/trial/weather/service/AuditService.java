package com.crossover.trial.weather.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.crossover.trial.weather.domain.Airport;
import com.crossover.trial.weather.domain.AuditReport;
import com.crossover.trial.weather.domain.Weather;

public class AuditService {
	
	private static final long ONE_DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);

	@Inject
	private WeatherRepository weatherRepository;
	
	private Map<String, Integer> airportQueries = new HashMap<>();

    private Map<Double, Integer> radiusQueries = new HashMap<>();
	
    public void auditWeatherSearch(String iataCode, double radiusKm) {
        airportQueries.put(iataCode, airportQueries.getOrDefault(iataCode, 0) + 1);
        radiusQueries.put(radiusKm, radiusQueries.getOrDefault(radiusKm, 0) + 1);
    }

    public AuditReport getReport() {
		AuditReport report = new AuditReport();
        report.setDatasize(countRecentNotEmptyDataPoints());
        report.setAirportFrequency(computeAirportRelativeFrequency());
        report.setRadiusFrequency(computeRadiusHistogram());
		return report;
	}

	private int[] computeRadiusHistogram() {
		int maxRadius = 1000;
		if (! radiusQueries.isEmpty()) {
			maxRadius = 10;
			for (double radius : radiusQueries.keySet()) {
				maxRadius = (int) Math.max(maxRadius, radius);
			}
		}

        int[] histogram = new int[maxRadius/10+1];
        for (Map.Entry<Double, Integer> e : radiusQueries.entrySet()) {
            int i = (int) (e.getKey() / 10); 
            histogram[i] += e.getValue();
        }
		return histogram;
	}

	private Map<String, Double> computeAirportRelativeFrequency() {
		double totalQueries = 0;
        for (Integer queriesCount : airportQueries.values()) {
        	totalQueries += queriesCount;
        }
        Map<String, Double> airportFrequency = new HashMap<>();
        for (String iataCode : airportQueries.keySet()) {
            double frequency = airportQueries.get(iataCode) / totalQueries; 
            airportFrequency.put(iataCode, frequency);
        }
		return airportFrequency;
	}

	private int countRecentNotEmptyDataPoints() {
		int count = 0;
        for (Airport airport : weatherRepository.getAllAirports()) {
        	Weather weather = airport.getWeather();
            if (!weather.isEmpty() && isRecent(weather)) {
                count++;
            }
        }
		return count;
	}
    
    private boolean isRecent(Weather weather) {
    	return weather.getLastUpdateTime() > System.currentTimeMillis() - ONE_DAY_IN_MILLIS;
    }
	
}
