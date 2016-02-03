package com.crossover.trial.properties.source;

import java.io.InputStream;

import com.crossover.trial.properties.ConfigException;

public class ClasspathConfigLoader extends StringPairStreamLoader {

	private static final String PREFIX = "classpath:";

	@Override
	public boolean canLoad(String location) {
		return location.startsWith(PREFIX);
	}

	@Override
	protected InputStream openStream(String location) {
		String classpathLocation = location.substring(PREFIX.length());
		InputStream is = ClasspathConfigLoader.class.getResourceAsStream(classpathLocation);
		if (is == null) {
			throw new ConfigException("Could not find classpath resource: " + classpathLocation);
		}
		return is;
	}

}
