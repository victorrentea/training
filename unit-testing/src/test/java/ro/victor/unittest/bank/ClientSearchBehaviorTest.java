package ro.victor.unittest.bank;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:client-search.feature", glue={"ro.victor.unittest.bank","cucumber.api.spring"})
public class ClientSearchBehaviorTest {

}
