package com.crossover.trial.properties.source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class URLConfigLoaderTest {

	private static final String HTTP_URL =  "https://raw.githubusercontent.com/victorrentea/training/master/spring-core/src/main/resources/config-basic.properties";
	
	@Test
	public void acceptsUris() {
		assertTrue(new URLConfigLoader().canLoad(HTTP_URL));
	}
	
	@Test
	public void opensUris() {
		Map<String, String> map = new URLConfigLoader().load(HTTP_URL);
		assertEquals("SA", map.get("jdbc.username"));
	}

}
