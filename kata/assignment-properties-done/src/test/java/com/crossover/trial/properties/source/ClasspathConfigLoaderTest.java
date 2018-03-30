package com.crossover.trial.properties.source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class ClasspathConfigLoaderTest {
	
	@Test
	public void acceptsUris() {
		assertTrue(new ClasspathConfigLoader().canLoad("classpath:/jdbc.properties"));
	}
	
	@Test
	public void opensUris() {
		Map<String, String> map = new ClasspathConfigLoader().load("classpath:/jdbc.properties");
		assertEquals("true", map.get("JPA_SHOWSQL"));
	}
	
	@Test
	public void opensUrisJSON() {
		Map<String, String> map = new ClasspathConfigLoader().load("classpath:/config.json");
		assertEquals("2.4", map.get("score.factor"));
	}

}
