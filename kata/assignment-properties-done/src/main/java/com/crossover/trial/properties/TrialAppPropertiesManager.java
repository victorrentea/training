package com.crossover.trial.properties;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crossover.trial.properties.definition.PropertyDefinition;
import com.crossover.trial.properties.definition.PropertyDefinitionsManager;
import com.crossover.trial.properties.source.ClasspathConfigLoader;
import com.crossover.trial.properties.source.FileSystemConfigLoader;
import com.crossover.trial.properties.source.StringPairLoader;
import com.crossover.trial.properties.source.URLConfigLoader;

/**
 * A simple main method to load and print properties. You should feel free to change this class
 * or to create additional class. You may add additional methods, but must implement the 
 * AppPropertiesManager API contract.
 * 
 * Note: a default constructor is required
 *
 * @author code test administrator
 */
public class TrialAppPropertiesManager implements AppPropertiesManager {
	
	private final static Logger log = LoggerFactory.getLogger(TrialAppPropertiesManager.class);
	
	private static final List<StringPairLoader> DEFAULT_LOADERS = new ArrayList<>(Arrays.asList(
			new ClasspathConfigLoader(),
			new FileSystemConfigLoader(),
			new URLConfigLoader()
			));
	
	private final List<StringPairLoader> loaders = new ArrayList<>(DEFAULT_LOADERS);
	
	private PropertyValueParser valueParser = new PropertyValueParser();
	
	private PropertyDefinitionsManager definitionsManager = new PropertyDefinitionsManager();

    @Override
    public AppProperties loadProps(List<String> configUris) {    	
    	Map<String, String> map = readAllConfigs(configUris);
    	return buildConfig(map);
    }

	private Map<String, String> readAllConfigs(List<String> propUris) {
		Map<String, String> accumulator = new HashMap<>();
    	for (String uri : propUris) {
    		Map<String, String> map = tryLoadConfigFrom(uri);
    		accumulator.putAll(map);
    	}
		return accumulator;
	}
	
	private AppProperties buildConfig(Map<String, String> accumulator) {
		List<String> missing = new ArrayList<>();
    	Map<String, Object> values = new HashMap<>();
    	for (String key : definitionsManager.getDefinitions().keySet()) {
    		PropertyDefinition definition = definitionsManager.getDefinitions().get(key);
    		String stringValue = accumulator.get(key);
    		if (stringValue==null) {
    			missing.add(key);
    		} else if (definition != null) {
    			values.put(key, parseValue(stringValue, definition));
    		} else {
    			log.warn("Ignoring unexpected property not defined: " + key);
    		}
    	}
        return new TrialAppProperties(values, missing);
	}

    private Object parseValue(String s, PropertyDefinition definition) {
    	if (s==null || s.isEmpty()) return null;
		switch (definition.getType()) {
		case STRING: return s;
		case INTEGER: return valueParser.parseInteger(s);
		case FLOAT: return valueParser.parseFloat(s);
		case BOOLEAN: return valueParser.parseBoolean(s);
		case URL: return valueParser.parseURL(s);
		case ENUM: return valueParser.parseEnum(s, definition.getEnumClass());
		default: throw new ConfigException("Not implemented");
		}
	}

	private Map<String, String> tryLoadConfigFrom(String uri) {
    	for (StringPairLoader loader : loaders) {
    		if (loader.canLoad(uri)) {
    			return loader.load(uri);
    		}
    	}
		throw new ConfigException("Could not determine a loader for uri: " + uri);
	}

	@Override
    public void printProperties(AppProperties props, PrintStream sync) {
        sync.println(props);
    }
	
	public void setValueParser(PropertyValueParser valueParser) {
		this.valueParser = valueParser;
	}
	
	public void addLoader(StringPairLoader loader) {
		loaders.add(loader);
	}
	
	public void setDefinitionsManager(PropertyDefinitionsManager definitionsManager) {
		this.definitionsManager = definitionsManager;
	}
}
