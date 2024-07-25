package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
public class NbpApiClient {
    private static final String BASE_URL = "http://api.nbp.pl/api/exchangerates/tables/A";

    public Response getCurrencyRates() {
        return RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .extract()
                .response();
    }
}
