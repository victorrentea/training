package com.crossover.trial.properties;

@SuppressWarnings("serial")
public class ConfigException extends RuntimeException {

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}
	
}
