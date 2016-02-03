package com.crossover.trial.properties;

import java.io.PrintStream;
import java.util.List;


/**
 * This provides programmatic interface to loading properties into your system and
 * checking the results of that load. You must implement this contract in your solution
 * in TrialPropertiesLoader.
 * 
 * Note: candidates should not change this interface
 *
 * @author code test administrator
 */
public interface AppPropertiesManager {

    /**
     * Given a list of URIs and set of required keys, construct an AppProperties object.
     *
     * @param propUris an ordered list of properties files to load, keys in later URIs override old keys
     * @return a fully constructed TrialProperties object
     */
    AppProperties loadProps(List<String> propUris);

    /**
     * Prints out all TrialProperties to the given PrintStream in sorted,
     * case insensitive, order by key name
     *
     * @param props properties to print
     * @param sync a stream to write the properties to
     */
    void printProperties(AppProperties props, PrintStream sync);
}
