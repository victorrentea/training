package com.crossover.trial.properties;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Extend this class to change parse logic. */
public class PropertyValueParser {

	private final static Logger log = LoggerFactory.getLogger(PropertyValueParser.class);

	public Boolean parseBoolean(String s) {
		if (s.equalsIgnoreCase("true")) return true;
		else if (s.equalsIgnoreCase("false")) return false;
		else {
			log.warn("Unable to parse boolean: " + s);
			return null;
		}
	}

	public Integer parseInteger(String s) {
		try {
			return Integer.parseInt(s.trim());
		} catch (NumberFormatException e) {
			log.warn("Unable to parse integer: " + s);
			return null;
		}
	}

	public Float parseFloat(String s) {
		try {
			return Float.parseFloat(s.trim());
		} catch (NumberFormatException e) {
			log.warn("Unable to parse float: " + s);
			return null;
		}
	}

	public URL parseURL(String s) {
		try {
			return new URL(s.trim());
		} catch (MalformedURLException e) {
			log.warn("Unable to parse URL: " + s);
			return null;
		}
	}

	public Enum<?> parseEnum(String s, Class<?> enumClass) {
		for (Object enumValueObj : enumClass.getEnumConstants()) {
			Enum<?> enumValue = (Enum<?>) enumValueObj;
			if (enumValue.name().equals(s)) return enumValue;
		}
		log.warn("Enum constant '" + s + "' not found in enum: " + enumClass);
		return null;
	}
}
