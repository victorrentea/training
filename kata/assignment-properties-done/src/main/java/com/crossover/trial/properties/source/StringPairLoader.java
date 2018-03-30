package com.crossover.trial.properties.source;

import java.util.Map;

public interface StringPairLoader {

	boolean canLoad(String location);
	
	Map<String, String> load(String location);
	
}
