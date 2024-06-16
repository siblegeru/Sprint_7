import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.CreateCourierStep;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class LoginCourierTest {
    private String login;
    private String password;
    private CreateCourierStep createCourierStep = new CreateCourierStep();




    //проверка возможности залогиниться
    @Test
    public void possibilityIsLoginCreationCourier() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);

        createCourierStep
                .createCourier(login, password, "");

        createCourierStep
                .loginSomeCourier(login, password)
                .statusCode(200)
                .body("id", notNullValue());
    }

    //проверка ошибки при незаполнении поля пароль
    @Test
    public void loginWithoutPassword() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);

        createCourierStep
                .createCourier(login, password, "");

        given()
                .contentType(ContentType.JSON)
                .baseUri("http://qa-scooter.praktikum-services.ru")
                .body("{\n" +
                        "   \"login\": \"" + login + "\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
        //не происходит обработка ошибки, тест падает по временным лимитам
    }

    //проверка ошибки при заполнении неправильного пароля
    @Test
    public void loginWithFalsePassword() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);

        createCourierStep
                .createCourier(login, password, "");

        createCourierStep
                .loginSomeCourier(login, "1234")
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    //передача всех обязательных полей для регистрации
    @Test
    public void passingAllImportantFields() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);

        createCourierStep
                .createCourier(login, password, "");

        createCourierStep
                .loginSomeCourier(login, password)
                .statusCode(200);
    }

    //авторизация под несуществующим пользователем
    @Test
    public void authorizationOnFalseUser() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);

        createCourierStep
                .createCourier(login, password, "");

        createCourierStep
                .loginSomeCourier("login", "password")
                .statusCode(404).body("message", is("Учетная запись не найдена"));
    }

    //успешный запрос возвращает id
    @Test
    public void bodyAnswerIsOk() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);

        createCourierStep
                .createCourier(login, password, "");

        createCourierStep
                .loginSomeCourier(login, password)
                .extract()
                .body()
                .path("id");
    }

    @After
    public void deleteCash(){
        Integer id = createCourierStep.loginSomeCourier(login, password)
                .extract().body().path("id");
        createCourierStep.delete(id);
    }
}
