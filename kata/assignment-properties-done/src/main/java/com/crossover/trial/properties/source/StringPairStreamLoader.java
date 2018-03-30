package com.crossover.trial.properties.source;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.crossover.trial.properties.ConfigException;
import com.crossover.trial.properties.format.ConfigTextParser;
import com.crossover.trial.properties.format.JsonParser;
import com.crossover.trial.properties.format.PropertiesParser;

public abstract class StringPairStreamLoader implements StringPairLoader {

	private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");
	
	private static final List<ConfigTextParser> PARSERS = new ArrayList<>(Arrays.asList(
			new JsonParser(),
			new PropertiesParser()
			));

	@Override
	public Map<String, String> load(String location) {
		if (!canLoad(location)) {
			throw new IllegalArgumentException("Cannot handle location: " + location);
		}
		String text = readText(location);
		return getParser(location, text).parseText(text);
	}

	protected String readText(String location)  {
		try (InputStream is = openStream(location)) {
			return IOUtils.toString(is, DEFAULT_ENCODING);
		} catch (IOException e) {
			throw new ConfigException("Cannot read config from: " + location, e);
		}
	}
	
	/** Caller closes the stream. */
	protected abstract InputStream openStream(String location);
	
	protected ConfigTextParser getParser(String location, String content) {
		for (ConfigTextParser parser : PARSERS) {
			if (parser.accepts(location, content)) {
				return parser;
			}
		}
		throw new ConfigException("Cannot determine parser for file name: '"+location+"'");
	}
	
}
