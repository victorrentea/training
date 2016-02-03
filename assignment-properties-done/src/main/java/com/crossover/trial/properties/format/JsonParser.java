package com.crossover.trial.properties.format;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class JsonParser implements ConfigTextParser {

	@Override
	public Map<String, String> parseText(String text) {
		JSONObject json = new JSONObject(text);
		Map<String, String> map = new HashMap<>();
		for (String key : json.keySet()) {
			map.put(key, json.optString(key));
		}
		return map;
	}

	
	@Override
	public boolean accepts(String location, String content) {
		return location.endsWith(".json");
	}

}
