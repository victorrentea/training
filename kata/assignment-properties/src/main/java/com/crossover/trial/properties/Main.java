package com.crossover.trial.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Provides example usage of the API and classes - although candidates can use this
 * Main method to test if their changes will be accepted by the autograder. If your
 * solution is incompatible with this main method, it will be incompatible with the
 * autograder and may cause your solution to be failed.
 * 
 * @author code test administrator
 */
public class Main {
    /**
     * Main method useful for your testing, this method is not tested by the grader.
     *
     * @param args
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {

        // process command line arguments into URIs
        File outputFile = new File(args[0]);
        if (outputFile.exists()) {
            outputFile.delete();
        }

        List<String> propertySourceUris = Arrays.asList(args).subList(1, args.length);

        // invoke the property parser and print out properties alphabetically
        AppPropertiesManager m = new TrialAppPropertiesManager();
        AppProperties props = m.loadProps(propertySourceUris);
        m.printProperties(props, new PrintStream(new FileOutputStream(outputFile)));
    }
}
