package com.crossover.trial.properties.format;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.crossover.trial.properties.format.PropertiesParser;

public class PropertiesParserTest {
	@Test
	public void parsesOk() {
		Map<String, String> map = new PropertiesParser().parseText("key1=a\nkey2=b\\c");
		assertEquals("a", map.get("key1"));
		assertEquals("bc", map.get("key2"));
	}
}
