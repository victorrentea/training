package com.crossover.trial.properties.format;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.crossover.trial.properties.ConfigException;

public class PropertiesParser implements ConfigTextParser {

	@Override
	public Map<String, String> parseText(String text) {
		StringReader reader = new StringReader(text);
		Properties properties = new Properties();
		try {
			properties.load(reader);
		} catch (IOException e) {
			throw new ConfigException("Could not parse properties file", e);
		}
		
		Map<String, String> map = new HashMap<>();
		for (final String name: properties.stringPropertyNames()) {
			map.put(name, properties.getProperty(name));
		}
		return map;
	}
	
	@Override
	public boolean accepts(String location, String content) {
		return location.endsWith(".properties");
	}

}
