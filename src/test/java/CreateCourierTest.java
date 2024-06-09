import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.CreateCourierStep;
import org.junit.After;
import org.junit.Test;


import static org.hamcrest.Matchers.*;

public class CreateCourierTest {

    private String login;
    private String password;
    private CreateCourierStep createCourierStep = new CreateCourierStep();

    //Проверка возможности создания курьера
    @Test
    public void possibilityCreateNewCurier() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        createCourierStep
                .createCourier(login, password, "")
                .statusCode(201);
    }

    //проверка тела положительного ответа при создании курьера
    @Test
    public void responseBodyIsOkTrue() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        createCourierStep
                .createCourier(login, password, "")
                .body("ok", is(true));
    }

    //проверка корректности кода ответа
    @Test
    public void responseCodeIsValid() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        createCourierStep
                .createCourier(login, password, "")
                .statusCode(201).body("ok", is(true));
    }

    //проверка необходимости заполнения обязательных полей для создания курьера
    @Test
    public void checkingWhetherRequiredFieldsAreRequired() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        createCourierStep
                .createCourier(login, password, "Saske")
                .statusCode(201);
    }

    //создание одинаковых курьеров
    @Test
    public void createSecondCourierWithOneData() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);

        createCourierStep
                .createCourier(login, password, "");

        createCourierStep
                .createCourier(login, password, "")
                .statusCode(409)
                .body("message", is("Этот логин уже используется"));
        // БАГ - значение message должно быть "Этот логин уже используется"
        // По факту получаем Этот логин уже используется. Попробуйте другой.
    }

    //пропуск обязательного поля при регистрации
    @Test
    public void checkWarningAnswer() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        login = RandomStringUtils.randomAlphabetic(10);
        password = "";
        createCourierStep
                .createCourier(login, password, "Saske")
                .statusCode(400).body("message", is("Недостаточно данных для создания учетной записи"));
    }


    @After
    public void deleteCash(){
        Integer id = createCourierStep.loginSomeCourier(login, password)
                .extract().body().path("id");
        if (id != null){
        createCourierStep.delete(id);
        }
    }

}
