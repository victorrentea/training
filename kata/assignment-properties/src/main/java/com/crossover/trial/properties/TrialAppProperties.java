package com.crossover.trial.properties;

import java.util.Collections;
import java.util.List;

/**
 * A dummy implementation of TrialAppProperties, this clearly doesn't work. Candidates SHOULD change 
 * this class to add their implementation. You are also free to create additional classes
 *
 * note: a default constructor is required.
 *
 * @author code test administrator
 */
public class TrialAppProperties implements AppProperties {

    @Override
    public List<String> getMissingProperties() {
        return Collections.emptyList();
    }

    @Override
    public List<String> getKnownProperties() {
        return Collections.emptyList();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object get(String key) {
        return "dummy";
    }
}

