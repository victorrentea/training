package com.crossover.trial.properties.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.crossover.trial.properties.ConfigException;

public class URLConfigLoader extends StringPairStreamLoader {

	@Override
	public boolean canLoad(String location) {
		return location.startsWith("https:") || location.startsWith("http:");
	}

	
	@Override
	protected InputStream openStream(String location) {
		try {
			URL url = new URL(location);
			return url.openStream();
		} catch (IOException e) {
			throw new ConfigException("Cannot open config file from URL: " + location,e);
		}
	}

}
