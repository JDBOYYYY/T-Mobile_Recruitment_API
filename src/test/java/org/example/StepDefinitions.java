package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class StepDefinitions {
    private static final Logger logger = LoggerFactory.getLogger(StepDefinitions.class);
    private Response response;
    private NbpApiClient apiClient = new NbpApiClient();

    @Given("I fetch the currency rates")
    public void i_fetch_the_currency_rates() {
        response = apiClient.getCurrencyRates();
        Assert.assertEquals(200, response.getStatusCode());
        logger.info("Fetched currency rates successfully with status code: {}", response.getStatusCode());
    }

    @Then("I should see the exchange rate for code {string}")
    public void i_should_see_the_exchange_rate_for_code(String code) {
        List<Map<String, Object>> rates = response.jsonPath().getList("[0].rates");
        rates.stream()
                .filter(rate -> rate.get("code").equals(code))
                .forEach(rate -> logger.info("Exchange rate for {}: {}", code, rate.get("mid")));
    }

    @Then("I should see the exchange rate for name {string}")
    public void i_should_see_the_exchange_rate_for_name(String name) {
        List<Map<String, Object>> rates = response.jsonPath().getList("[0].rates");
        rates.stream()
                .filter(rate -> rate.get("currency").equals(name))
                .forEach(rate -> logger.info("Exchange rate for {}: {}", name, rate.get("mid")));
    }

    @Then("I should see currencies with rate above {double}")
    public void i_should_see_currencies_with_rate_above(Double rateThreshold) {
        List<Map<String, Object>> rates = response.jsonPath().getList("[0].rates");
        rates.stream()
                .filter(rate -> ((Number) rate.get("mid")).doubleValue() > rateThreshold)
                .forEach(rate -> logger.info("Currency with rate above {}: {} - {}", rateThreshold, rate.get("currency"), rate.get("mid")));
    }

    @Then("I should see currencies with rate below {double}")
    public void i_should_see_currencies_with_rate_below(Double rateThreshold) {
        List<Map<String, Object>> rates = response.jsonPath().getList("[0].rates");
        rates.stream()
                .filter(rate -> ((Number) rate.get("mid")).doubleValue() < rateThreshold)
                .forEach(rate -> logger.info("Currency with rate below {}: {} - {}", rateThreshold, rate.get("currency"), rate.get("mid")));
    }
}
