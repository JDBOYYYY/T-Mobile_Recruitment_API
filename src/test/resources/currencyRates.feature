Feature: Currency Rates

  Scenario: Kursy walut
    Given I fetch the currency rates
    Then I should see the exchange rate for code "USD"
    And I should see the exchange rate for name "dolar amerykański"
    And I should see currencies with rate above 5
    And I should see currencies with rate below 3
