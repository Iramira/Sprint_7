import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CreateCourier;
import org.example.LoginCourier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.example.CourierController.executeCreate;
import static org.example.CourierController.executeLogin;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginCourierParameterizedTest {
    private final LoginCourier pathToLogin; // pathToLogin - путь к JSON с учётными данными для логина курьера

    public LoginCourierParameterizedTest(LoginCourier pathToLogin) {
        this.pathToLogin = pathToLogin;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] getJsonLoginVariable() {
        return new Object[][] {
                {new LoginCourier("","12345")},
                {new LoginCourier("IRAMIRAIRA","")}
        };
    }
    @Test
    public void failLoginTestWithoutRequiredFields() {
        CreateCourier createCourier = new CreateCourier("IRAMIRAIRA","12345","IRAMIRA");
        executeCreate(createCourier);
        Response response = executeLogin(pathToLogin);
        // Проверить наличие ошибки при отправлении неверных учётных данных (отсутствие логина или пароля)
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
}
