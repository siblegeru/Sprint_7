package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static io.restassured.RestAssured.given;

@Getter
@Setter
public class CreateSomeOrderStep {

    private String firstName;
    private String lastName;
    private String address;
    private Integer metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public ValidatableResponse createNewOrder(List<String> color) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri("http://qa-scooter.praktikum-services.ru")
                .body("{\n" +
                        "    \"firstName\": \"Naruto\",\n" +
                        "    \"lastName\": \"Uchiha\",\n" +
                        "    \"address\": \"Konoha, 142 apt.\",\n" +
                        "    \"metroStation\": 4,\n" +
                        "    \"phone\": \"+7 800 355 35 35\",\n" +
                        "    \"rentTime\": 5,\n" +
                        "    \"deliveryDate\": \"2020-06-06\",\n" +
                        "    \"comment\": \"Saske, come back to Konoha\",\n" +
                        "    \"color\": \n" +
                        "        [\"" + color + "\"]\n" +
                        "}")
                .when()
                .post("/api/v1/orders")
                .then();
    }


    public ValidatableResponse deleteOrder(Integer track) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri("http://qa-scooter.praktikum-services.ru")
                .pathParams("track", track)
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }


}
