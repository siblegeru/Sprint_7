import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ListOrdersTest {

    //получение списка заказов
    @Test
    public void listOrderTest(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        given()
                .contentType(ContentType.JSON)
                .baseUri("http://qa-scooter.praktikum-services.ru")
                .when()
                .get("/api/v1/orders/")
                .then()
                .statusCode(200)
                .extract().response();
    }
}
