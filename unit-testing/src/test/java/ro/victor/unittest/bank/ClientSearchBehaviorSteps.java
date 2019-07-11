package ro.victor.unittest.bank;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ContextConfiguration;
import ro.victor.unittest.bank.entity.Client;
import ro.victor.unittest.bank.repo.ClientJpaRepository;
import ro.victor.unittest.bank.repo.ClientSearchCriteria;
import ro.victor.unittest.bank.repo.ClientSearchResult;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(classes = BankApplication.class)
public class ClientSearchBehaviorSteps {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ClientJpaRepository repository;

    private Client client;
    private ClientSearchCriteria searchCriteria = new ClientSearchCriteria();


    @Given("^A Client exists in DB$")
    public void a_Client_exists_in_DB() throws Throwable {
        client = new Client();
        entityManager.persist(client);
    }


    @Given("^The Client has name \"([^\"]*)\"$")
    public void the_Client_has_name(String clientName) throws Throwable {
        client.setName(clientName);
    }


    @When("^Search criteria name=\"([^\"]*)\"$")
    public void search_criteria_name(String searchName) throws Throwable {
        searchCriteria.setName(searchName);
    }

    @Then("^The Client is returned$")
    public void the_Client_is_returned() throws Throwable {
        List<ClientSearchResult> results = repository.search(searchCriteria);
        assertFalse(results.isEmpty());
        assertEquals((long)client.getId(), results.get(0).getId());
    }

    @Then("^No results are returned$")
    public void noResultsAreReturned() {
        List<ClientSearchResult> results = repository.search(searchCriteria);
        assertTrue(results.isEmpty());
    }
}
