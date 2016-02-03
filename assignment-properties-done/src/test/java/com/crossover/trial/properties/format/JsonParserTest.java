package com.crossover.trial.properties.format;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.crossover.trial.properties.format.JsonParser;

public class JsonParserTest {

	@Test
	public void parsesJsonOK() {
		String json = "{   \"uri.key\": \"https://authserver/v1/auth\", "
				+ "\"integer.key\": 3600, \"string.key\": \"broadcast\", "
				+ "\"float.key\": 2.4, \"boolean.key\": false }";
		Map<String, String> map = new JsonParser().parseText(json);
		assertEquals("broadcast", map.get("string.key"));
		assertEquals("3600", map.get("integer.key"));
		assertEquals("2.4", map.get("float.key"));
		assertEquals("false", map.get("boolean.key"));
		assertEquals("https://authserver/v1/auth", map.get("uri.key"));
	}
}
