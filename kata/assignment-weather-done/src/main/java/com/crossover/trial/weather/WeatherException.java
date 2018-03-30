package com.crossover.trial.weather;

@SuppressWarnings("serial")
public class WeatherException extends RuntimeException {

	public WeatherException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeatherException(String message) {
		super(message);
	}

	public WeatherException(Throwable cause) {
		super(cause);
	}
	
}
