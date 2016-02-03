package com.crossover.trial.properties.format;

import java.util.Map;

public interface ConfigTextParser {

	Map<String, String> parseText(String text);

	boolean accepts(String location, String content);
}
