package com.crossover.trial.properties.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.crossover.trial.properties.ConfigException;

public class FileSystemConfigLoader extends StringPairStreamLoader {

	private static final String PREFIX = "file://";

	@Override
	public boolean canLoad(String location) {
		return location.startsWith(PREFIX);
	}

	@Override
	protected InputStream openStream(String location) {
		File file = new File(location.substring(PREFIX.length()));
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ConfigException(e);
		}
	}

}
