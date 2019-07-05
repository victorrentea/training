package ro.victor.unittest.bdd.filtering;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:export-filters.feature"}, glue={"ro.victor.unittest.bdd.filtering"})
public class ExportFiltersBehaviorTest {

}
