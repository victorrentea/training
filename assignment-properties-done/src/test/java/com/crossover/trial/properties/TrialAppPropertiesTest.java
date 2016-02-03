package com.crossover.trial.properties;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class TrialAppPropertiesTest {
	
	@Test
	public void toStringReturnsOk() {
		TrialAppProperties props = new TrialAppProperties(Collections.singletonMap("key1", 12));
		assertEquals("key1, java.lang.Integer, 12", props.toString());
	}
	
	@Test
	public void testGetByMixedCaseAnd_DotOK() {
		TrialAppProperties props = new TrialAppProperties(Collections.singletonMap("kEy_1.2", 12));
		assertEquals(12, props.get("Key.1_2"));
	}
	
	@Test
	public void testGetByNonExistingKey() {
		TrialAppProperties props = new TrialAppProperties(Collections.singletonMap("key1", 12));
		assertNull(props.get("key2"));
	}
	
	@Test
	public void knownKeysIncludesAll() {
		TrialAppProperties props = new TrialAppProperties(Collections.singletonMap("key1", 12), Arrays.asList("missingKey"));
		assertThat(props.getKnownProperties(), hasItem("key1"));
		assertThat(props.getKnownProperties(), hasItem("missingKey"));
	}
	
	@Test
	public void isValidFailsWhenMissing() {
		TrialAppProperties props = new TrialAppProperties(Collections.singletonMap("key1", 12), Arrays.asList("missingKey"));
		assertFalse(props.isValid());
	}
	
	@Test
	public void clearErasesAll() {
		TrialAppProperties props = new TrialAppProperties(Collections.singletonMap("key1", 12));
		assertNotNull(props.get("key1"));
		assertTrue(props.getMissingProperties().isEmpty());
		props.clear();
		assertNull(props.get("key1"));
		assertThat(props.getMissingProperties(), hasItem("key1"));
	}

}
