package com.crossover.trial.properties.definition;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Test;

import com.amazonaws.regions.Regions;
import com.crossover.trial.properties.ConfigException;

public class PropertyDefinitionsManagerTest {
	
	@After
	public void clearDefinitionsResourceNameSysProperty() {
		System.clearProperty(PropertyDefinitionsManager.DEFINITIONS_RESOURCE_NAME_SYS_PROPERTY);
	}

	@Test
	public void defaultDefinitionsAreLoadedOK() {
		Map<String, PropertyDefinition> map = new PropertyDefinitionsManager().getDefinitions();
		assertThat(map.get("string.key"), is(aDefinition("string.key", PropertyType.STRING)));
		assertThat(map.get("float.key"), is(aDefinition("float.key", PropertyType.FLOAT)));
		assertThat(map.get("integer.key"), is(aDefinition("integer.key", PropertyType.INTEGER)));
		assertThat(map.get("boolean.key"), is(aDefinition("boolean.key", PropertyType.BOOLEAN)));
		assertThat(map.get("uri.key"), is(aDefinition("uri.key", PropertyType.URL)));
	}
	
	@Test
	public void enumDefinitionIsLoadedOK() {
		Map<String, PropertyDefinition> map = new PropertyDefinitionsManager().getDefinitions();
		assertEquals(Regions.class, map.get("enum.key").getEnumClass());
		assertEquals(PropertyType.ENUM, map.get("enum.key").getType());
	}
	
	@Test
	public void customLocatedDefinitionsAreLoadedOK() {
		System.setProperty(PropertyDefinitionsManager.DEFINITIONS_RESOURCE_NAME_SYS_PROPERTY, "custom-definitions-ok.properties");
		Map<String, PropertyDefinition> map = new PropertyDefinitionsManager().getDefinitions();
		assertThat(map.get("string.key2"), is(aDefinition("string.key2", PropertyType.STRING)));
	}
	
	@Test(expected=ConfigException.class)
	public void errorneousPropertyType() {
		System.setProperty(PropertyDefinitionsManager.DEFINITIONS_RESOURCE_NAME_SYS_PROPERTY, "custom-definitions-error.properties");
		new PropertyDefinitionsManager().getDefinitions();
	}
	
	@Test(expected=ConfigException.class)
	public void nonExistentDefinitionsFile() {
		System.setProperty(PropertyDefinitionsManager.DEFINITIONS_RESOURCE_NAME_SYS_PROPERTY, "custom-definitions-unexistent.properties");
		new PropertyDefinitionsManager().getDefinitions();
	}
	
	
	
	
	
	
	private static Matcher<PropertyDefinition> aDefinition(String name, PropertyType propertyType) {
		return new BaseMatcher<PropertyDefinition>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof PropertyDefinition)) return false;
				PropertyDefinition definition = (PropertyDefinition) item;
				return name.equals(definition.getName()) && propertyType.equals(definition.getType());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("is a PropertyDescription with name="+name+" and type="+propertyType);
			}
		};
	}
}
