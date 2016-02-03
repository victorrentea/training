package com.crossover.trial.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A dummy implementation of TrialAppProperties, this clearly doesn't work. Candidates SHOULD change 
 * this class to add their implementation. You are also free to create additional classes
 *
 * note: a default constructor is required.
 *
 * @author code test administrator
 */
public class TrialAppProperties implements AppProperties {
	
	private final Map<String, Object> normalizedMap = new HashMap<>();
	private final Map<String, Object> originalMap = new TreeMap<>();
	private final List<String> missingKeys;
	
	
	public TrialAppProperties(Map<String, Object> values) {
		this(values, Collections.emptyList());
	}
	
    public TrialAppProperties(Map<String, Object> values, List<String> missingKeys) {
    	for (String key : values.keySet()) {
    		this.normalizedMap.put(normalize(key), values.get(key));
    	}
    	this.originalMap.putAll(values);
		this.missingKeys = new ArrayList<>(missingKeys);
	}

	public TrialAppProperties() {
    	this(Collections.emptyMap(), Collections.emptyList());
    }
	
	@Override
    public List<String> getMissingProperties() {
        return missingKeys;
    }

    @Override
    public List<String> getKnownProperties() {
        List<String> knownKeys = new ArrayList<>();
        knownKeys.addAll(originalMap.keySet());
        knownKeys.addAll(missingKeys);
		return knownKeys;
    }

    @Override
    public boolean isValid() {
        return missingKeys.isEmpty();
    }

    @Override
    public void clear() {
    	missingKeys.addAll(normalizedMap.keySet());
    	normalizedMap.clear();
    }

    @Override
    public Object get(String key) {
        return normalizedMap.get(normalize(key));
    }
    
    @Override
    public String toString() {
    	String s ="";
    	for (String key : originalMap.keySet()) {
    		Object value = originalMap.get(key);
    		s += key + ", " + value.getClass().getName() + ", " + value;
    	}
    	return s;
    }
    
    private String normalize(String key) {
    	return key.toLowerCase().replace('_', '.');
    }
}

