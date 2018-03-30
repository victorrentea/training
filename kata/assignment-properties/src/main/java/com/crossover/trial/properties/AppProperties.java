package com.crossover.trial.properties;

import java.util.List;

/**
 * Generic interface for access loaded system properties.
 *
 * Note: Candidates should not change this interface.
 *
 * @author code test administrator
 */
public interface AppProperties {

    /**
     * @return a list of properties that are unset either because they are missing or because they have the wrong type
     */
    List<String> getMissingProperties();

    /**
     * @return a list of all known keys
     */
    List<?> getKnownProperties();

    /**
     * @return true if the configuration is valid, false otherwise
     */
    boolean isValid();

    /**
     * resets all loaded properties to null / unloaded
     */
    void clear();

    /**
     *  Retrieve the property for the given key. Keys are case-insenstive
     *  and the use of . and _ in property names is interchangable. For example,
     *  jpa.showSQL, jpa_showsql and JPA_showSql should all retrieve the same value.
     *
     * @param key a property key, handled without case sensitivity. '.' and '_' are
     *            treated as equivalent
     * @return an object of the given key or null if it is not available
     */
    Object get(String key);
}
