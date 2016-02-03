package com.crossover.trial.properties.definition;

public class PropertyDefinition {
	private final String name;
	private final PropertyType type;
	private final Class<?> enumClass;

	public PropertyDefinition(String name, PropertyType type) {
		this(name, type, null);
	}

	public PropertyDefinition(String name, Class<?> enumClassName) {
		this(name, PropertyType.ENUM, enumClassName);
	}

	private PropertyDefinition(String name, PropertyType type, Class<?> enumClass) {
		if (enumClass != null && !enumClass.isEnum()) {
			throw new IllegalArgumentException("Not an enum class: " + enumClass);
		}
		this.name = name;
		this.type = type;
		this.enumClass = enumClass;
	}

	public final String getName() {
		return name;
	}

	public final PropertyType getType() {
		return type;
	}

	public final Class<?> getEnumClass() {
		return enumClass;
	}

}
