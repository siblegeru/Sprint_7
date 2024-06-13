import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.example.CreateSomeOrderStep;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    private CreateSomeOrderStep createSomeOrderStep = new CreateSomeOrderStep();
    private List<String> color;

    @Parameters(name = "{index} color = {0}")
    public static Object[][] data(){
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of("")},
        };
    }
    public CreateOrderTest(List<String> color){
        this.color = color;
    }

    @Test
    public void newOrderCheckTest(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        createSomeOrderStep
                .createNewOrder(color)
                .statusCode(201);
    }

    @Test
    public void responseCodeIsValid() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        createSomeOrderStep
                .createNewOrder(color)
                .statusCode(201).extract().body().path("track");
    }

}


