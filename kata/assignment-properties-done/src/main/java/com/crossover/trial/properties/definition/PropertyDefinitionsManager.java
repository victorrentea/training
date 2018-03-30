package com.crossover.trial.properties.definition;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.crossover.trial.properties.ConfigException;

public class PropertyDefinitionsManager {

	public static final String DEFINITIONS_RESOURCE_NAME_SYS_PROPERTY = "propertyDefinitionsResourceName";

	public static final String DEFAULT_DEFINITIONS_RESOURCE_NAME = "/properties-definitions.properties";

	private final Map<String, PropertyDefinition> definitions;

	public PropertyDefinitionsManager() {
		definitions = loadDefinitions();
	}
	
	public Map<String, PropertyDefinition> getDefinitions() {
		return definitions;
	}

	private Map<String, PropertyDefinition> loadDefinitions() {
		Properties properties = readDefinitions();
		
		Map<String, PropertyDefinition> map = new HashMap<>();
		for (Object nameObj:properties.keySet()) {
			String name = (String) nameObj;
			String value = properties.getProperty(name);
			map.put(name, createDefinition(name, value));
		}
		return Collections.unmodifiableMap(map);
	}

	private PropertyDefinition createDefinition(String name, String value) {
		if (value.startsWith("ENUM")) {
			if (value.length()<"ENUM:".length()+1) {
				throw new ConfigException("The type ENUM must be followed by ':' and then the fully qualified class name of the enum");
			}
			String className = value.substring("ENUM:".length());
			try {
				Class<?> enumClass = Class.forName(className);
				if (!enumClass.isEnum()) {
					new ConfigException("Could not load property definition '" + name + "': Target class is not an enum: " + className);
				}
				return new PropertyDefinition(name, enumClass);
			} catch (ReflectiveOperationException e) {
				throw new ConfigException("Could not load property definition '" + name + "': Could not load target enum class", e);
			}
		} else {
			try {
				PropertyType type = PropertyType.valueOf(value.toUpperCase());
				return new PropertyDefinition(name, type);
			} catch (IllegalArgumentException e) {
				throw new ConfigException("Invalid property type for property '" + name + "'. Valid values: "
						+ Arrays.toString(PropertyType.values()), e);
			}
		}
	}

	private Properties readDefinitions() {
		String resourceName = System.getProperty(DEFINITIONS_RESOURCE_NAME_SYS_PROPERTY, DEFAULT_DEFINITIONS_RESOURCE_NAME);
		try (InputStream is = PropertyDefinitionsManager.class.getResourceAsStream(resourceName)) {
			if (is == null) {
				throw new ConfigException("Could not read configuration definitions. Classpath resource not found: " + resourceName);
			}
			Properties properties = new Properties();
			properties.load(is);
			return properties;
		} catch (IOException e) {
			throw new ConfigException("Could not read configuration definitions from classpath resource: " + resourceName);
		}
	}

}
