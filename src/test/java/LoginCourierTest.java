import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CreateCourier;
import org.example.LoginCourier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.example.CourierController.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @Test
    public void successCourierLoginTest() {
        CreateCourier createCourier = new CreateCourier("IRAMIRAIRA","12345","IRAMIRA");
        executeCreate(createCourier);
        LoginCourier loginCourier = new LoginCourier("IRAMIRAIRA","12345");
        Response response = executeLogin(loginCourier);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void failLoginNonExistCourierTest() {
        LoginCourier loginCourier = new LoginCourier("JolineKudjo","asdksa%jkhdk!");
        Response response = executeLogin(loginCourier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    public void failLoginWithWrongLoginTest() {
        LoginCourier loginCourier = new LoginCourier("IRAMIRAIRA123","12345");
        Response response = executeLogin(loginCourier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    public void failLoginWithWrongPasswordTest() {
        LoginCourier loginCourier = new LoginCourier("IRAMIRAIRA","123452937dsfhjl");
        Response response = executeLogin(loginCourier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @After
    public void deleteChanges() {
        LoginCourier loginCourier = new LoginCourier("IRAMIRAIRA","12345");
        executeDelete(loginCourier);
    }
}