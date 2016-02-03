package com.crossover.trial.weather.domain;

import java.util.HashMap;
import java.util.Map;

public class AuditReport {
	private int datasize;
	private Map<String, Double> airportFrequency = new HashMap<>();
	private int[] radiusFrequency;

	public final int getDatasize() {
		return datasize;
	}

	public final void setDatasize(int datasize) {
		this.datasize = datasize;
	}

	public final Map<String, Double> getAirportFrequency() {
		return airportFrequency;
	}

	public final void setAirportFrequency(Map<String, Double> airportFrequency) {
		this.airportFrequency = airportFrequency;
	}

	public final int[] getRadiusFrequency() {
		return radiusFrequency;
	}

	public final void setRadiusFrequency(int[] radiusFrequency) {
		this.radiusFrequency = radiusFrequency;
	}

}
