package com.crossover.trial.properties;

import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.crossover.trial.properties.definition.PropertyDefinition;
import com.crossover.trial.properties.definition.PropertyDefinitionsManager;
import com.crossover.trial.properties.definition.PropertyType;
import com.crossover.trial.properties.source.StringPairLoader;

@RunWith(MockitoJUnitRunner.class)
public class TrialAppPropertiesManagerTest {

	private TrialAppPropertiesManager target = new TrialAppPropertiesManager();
	
	@Mock
	private StringPairLoader mockLoader;
	
	@Mock
	private PropertyDefinitionsManager mockDefinitions;

	private Map<String, String> stringPairs = new HashMap<>(singletonMap("key1", "1"));

	private Map<String, PropertyDefinition> definitions = new HashMap<>(singletonMap("key1", new PropertyDefinition("key1", PropertyType.INTEGER)));

	@Before
	public void configureTarget() {
		when(mockLoader.canLoad(Matchers.startsWith("test:"))).thenReturn(true);
		when(mockLoader.load("test:test")).thenAnswer(new Answer<Map<String,String>>() {
			@Override
			public Map<String, String> answer(InvocationOnMock invocation) throws Throwable {
				return stringPairs;
			}
		});
		when(mockDefinitions.getDefinitions()).thenAnswer(new Answer<Map<String, PropertyDefinition>>() {
			@Override
			public Map<String, PropertyDefinition> answer(InvocationOnMock invocation) throws Throwable {
				return definitions;
			}
		});
		target.addLoader(mockLoader);
		target.setDefinitionsManager(mockDefinitions);
	}
	
	@Test
	public void normalFlow() {
		AppProperties props = target.loadProps(Arrays.asList("test:test"));
		assertEquals(1, props.get("key1"));
	}
	
	@Test
	public void overrideProperties() {
		when(mockLoader.load("test:test2")).thenReturn(singletonMap("key1", "2"));
		AppProperties props = target.loadProps(Arrays.asList("test:test", "test:test2"));
		assertEquals(2, props.get("key1"));
	}

	@Test
	public void overrideWithEmpty() {
		when(mockLoader.load("test:test2")).thenReturn(singletonMap("key1", ""));
		AppProperties props = target.loadProps(Arrays.asList("test:test", "test:test2"));
		assertNull(props.get("key1"));
	}

	@Test
	public void incompatibleValueForDatatype() {
		when(mockLoader.load("test:test")).thenReturn(singletonMap("key1", "aa"));
		AppProperties props = target.loadProps(Arrays.asList("test:test"));
		assertNull(props.get("key1"));
	}

	@Test
	public void overrideWithInvalid() {
		when(mockLoader.load("test:test2")).thenReturn(singletonMap("key1", "aa"));
		AppProperties props = target.loadProps(Arrays.asList("test:test", "test:test2"));
		assertNull(props.get("key1"));
	}


	@Test
	public void missingProperties() {
		definitions.put("key2", new PropertyDefinition("key2", PropertyType.STRING));
		when(mockLoader.load("test:test")).thenReturn(singletonMap("key1", "aa"));
		AppProperties props = target.loadProps(Arrays.asList("test:test"));
		assertThat(props.getMissingProperties(), hasItem("key2"));
	}
	
	@Test
	public void additionalPropertiesNotDefined() {
		when(mockLoader.load("test:test")).thenReturn(singletonMap("key2", "aa"));
		AppProperties props = target.loadProps(Arrays.asList("test:test"));
		assertNull(props.get("key2"));
	}

}