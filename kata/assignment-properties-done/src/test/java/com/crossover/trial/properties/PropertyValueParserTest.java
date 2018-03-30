package com.crossover.trial.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import com.crossover.trial.properties.definition.PropertyType;

public class PropertyValueParserTest {
	private PropertyValueParser parser = new PropertyValueParser();

	@Test
	public void parseBoolean() {
		assertEquals(true, parser.parseBoolean("true"));
		assertNull(parser.parseBoolean("yes"));
	}

	@Test
	public void parseInteger() {
		assertEquals(1, (int) parser.parseInteger("1"));
		assertEquals(-2, (int) parser.parseInteger("-2"));
		assertEquals(0, (int) parser.parseInteger("0"));
		assertNull(parser.parseInteger("a"));
		assertNull(parser.parseInteger("2.4"));
	}

	@Test
	public void parseFloat() {
		assertEquals(1.2, (float) parser.parseFloat("1.2"), 0.00001);
		assertEquals(1, (float) parser.parseFloat("1"), 0.00001);
		assertNull(parser.parseFloat("a"));
	}

	@Test
	public void parseEnum() {
		assertEquals(PropertyType.STRING, parser.parseEnum("STRING", PropertyType.class));
		assertNull(parser.parseEnum("X", PropertyType.class));
		assertNull(parser.parseEnum(null, PropertyType.class));
	}

	@Test
	public void parseUrl() throws URISyntaxException, MalformedURLException {
		assertEquals(new URL("http://www.google.com"), parser.parseURL("http://www.google.com"));
		assertNull(parser.parseURL("htwww.google.com"));
	}
}
