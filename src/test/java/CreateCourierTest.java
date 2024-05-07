import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CreateCourier;
import org.example.LoginCourier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.example.CourierController.*;
import static org.hamcrest.Matchers.equalTo;
public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void successSingleTest() {
        CreateCourier createCourier = new CreateCourier("IRAMIRAIRA","12345","IRAMIRA");
        LoginCourier loginCourier = new LoginCourier("IRAMIRAIRA","12345");
        if (executeLogin(loginCourier).getStatusCode() == SC_OK) {
            executeDelete(loginCourier);
        } else {
            Response response = executeCreate(createCourier);
            response.then().assertThat().body("ok", equalTo(true))
                    .and()
                    .statusCode(SC_CREATED);
        }
    }

    @Test
    public void failDoubleTest() {
        CreateCourier createCourier = new CreateCourier("IRAMIRAIRA","12345","IRAMIRA");
        executeCreate(createCourier);
        Response response = executeCreate(createCourier);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);

    }

    @After
    public void deleteChanges() {
        LoginCourier loginCourier = new LoginCourier("IRAMIRAIRA","12345");
        executeDelete(loginCourier);
    }
}
