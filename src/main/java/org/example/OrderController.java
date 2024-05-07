package org.example;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderController {
    private final static String apiOrders = "/api/v1/orders";

    public static Response executeOrder(CreateOrder createOrder) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(createOrder)
                        .when()
                        .post(apiOrders);
        return response;
    }

    public static Response executeGetOrderByTrackId(int trackId) {
        return given()
                .header("Content-type", "application/json")
                .queryParam("t",trackId)
                .when()
                .get(apiOrders + "/track");
    }
    public static int parseOrderIdFromResponse(Response response) {
        int trackId = response.jsonPath().get("track");
        Response responseOrder = executeGetOrderByTrackId(trackId);
        return responseOrder.jsonPath().get("order.id");
    }

    public static Response executeAcceptOrder(int courierId, int orderId) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .queryParam("courierId", courierId)
                        .when()
                        .put(String.format(apiOrders + "/accept/%s",orderId));
        return response;
    }

    public static Response executeListOrder(int courierId) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .queryParam("courierId", courierId)
                        .when()
                        .get(apiOrders);
        return response;
    }

    public static Response executeListOrder(Map<String,String> queryParams) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .queryParams(queryParams)
                        .when()
                        .get(apiOrders);
        return response;
    }

    public static Response executeDeleteOrder(int orderId) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .put(String.format(apiOrders + "/finish/%s",orderId));
        return response;
    }

    public static Response executeDeleteOrder(CreateOrder createOrder) {
        return executeDeleteOrder(createOrder);
    }
}
