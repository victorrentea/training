package ro.victor.unittest.bank;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import ro.victor.unittest.bank.entity.Account;
import ro.victor.unittest.bank.entity.Client;
import ro.victor.unittest.bank.repo.ClientJdbcRepository;
import ro.victor.unittest.bank.repo.ClientJpaRepository;
import ro.victor.unittest.bank.vo.ClientSearchCriteria;
import ro.victor.unittest.bank.vo.ClientSearchResult;
import ro.victor.unittest.time.TestTimeRule;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(classes = BankApplication.class)
public class ClientSearchBehaviorSteps {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ClientJdbcRepository repository;

    private Client client;
    private ClientSearchCriteria criteria = new ClientSearchCriteria();

    @Rule
    private TestTimeRule testTime = new TestTimeRule();


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
        criteria.setName(searchName);
    }

    @Then("^The Client is returned$")
    public void the_Client_is_returned() throws Throwable {
        entityManager.flush(); // needed only for non raw JDBC access
        List<ClientSearchResult> results = repository.search(criteria);
        assertFalse(results.isEmpty());
        assertEquals((long)client.getId(), results.get(0).getId());
    }

    @Then("^No results are returned$")
    public void noResultsAreReturned() {
        entityManager.flush(); // needed only for non raw JDBC access
        List<ClientSearchResult> results = repository.search(criteria);
        assertTrue(results.isEmpty());
    }

    @Given("^The Client has birthDate \"([^\"]*)\"$")
    public void theClientHasBirthDate(String dateStr) {
        client.setBirthDate(LocalDate.parse(dateStr));
    }

    @And("^Today is \"([^\"]*)\"$")
    public void todayIs(String dateStr) {
        testTime.setTestDate(LocalDate.parse(dateStr));
    }

    @When("^Search criteria min age=\"([^\"]*)\"$")
    public void searchMinAge(Integer minAge) {
        criteria.setMinAge(minAge);
    }

    @When("^Search criteria max age=\"([^\"]*)\"$")
    public void searchMaxAge(Integer maxAge) {
        criteria.setMaxAge(maxAge);
    }

    @Given("^The Client has nationality iso \"([^\"]*)\"$")
    public void theClientHasNationalityIso(String iso) throws Throwable {
        client.setNationalityIso(iso);
    }

    @When("^Search criteria nationality iso = \"([^\"]*)\"$")
    public void searchCriteriaNationalityIso(List<String> isoList) throws Throwable {
        criteria.setNationalityIsoList(isoList);
    }

    @Then("^The Client is returned: \"([^\"]*)\"$")
    public void theClientIsReturned(Boolean bool) throws Throwable {
        if (bool) {
            the_Client_is_returned();
        } else {
            noResultsAreReturned();
        }
    }

    @Given("^The Client has an IBAN \"([^\"]*)\"$")
    public void theClientHasAnIBAN(String iban) throws Throwable {
        client.add(new Account(iban, Account.Type.DEBIT));
    }

    @When("^Search criteria iban = \"([^\"]*)\"$")
    public void searchCriteriaIban(String iban) throws Throwable {
        criteria.setIban(iban);
    }
}
