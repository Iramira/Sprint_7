package org.example;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierController {
    private final static String apiCourier = "/api/v1/courier";
    public static Response executeCreate(CreateCourier createCourier) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(createCourier)
                        .when()
                        .post(apiCourier);
        return response;
    }

    private static Response executeDelete(int courierId) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete(String.format(apiCourier + "%s", courierId));
        return response;
    }

    public static Response executeDelete(LoginCourier loginCourier) {
        return executeDelete(getCourierId(loginCourier));
    }

    public static int getCourierId(LoginCourier loginCourier) {
        Response response = executeLogin(loginCourier);
        int id = response.jsonPath().get("id");
        return id;
    }
    public static Response executeLogin(LoginCourier loginCourier) {

        return
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourier)
                        .when()
                        .post(apiCourier + "/login");
    }
}
