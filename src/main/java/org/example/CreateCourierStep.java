package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CreateCourierStep {

    public ValidatableResponse createCourier(String login, String password, String firstName){
        return given()
                .contentType(ContentType.JSON)
                .baseUri("http://qa-scooter.praktikum-services.ru")
                .body("{\n" +
                     "   \"login\": \"" + login + "\",\n" +
                     "   \"password\": \"" + password + "\",\n" +
                     "    \"firstName\": \"saske\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse loginSomeCourier(String login, String password) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri("http://qa-scooter.praktikum-services.ru")
                .body("{\n" +
                        "   \"login\": \"" + login + "\",\n" +
                        "   \"password\": \"" + password + "\",\n" +
                        "    \"firstName\": \"saske\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier/login")
                .then();
    }


    public ValidatableResponse delete(Integer id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri("http://qa-scooter.praktikum-services.ru")
                .pathParams("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }
}
