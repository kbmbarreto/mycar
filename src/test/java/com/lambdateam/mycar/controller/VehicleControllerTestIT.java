package com.lambdateam.mycar.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehicleControllerTestIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateUser() {
        String createVehicleJSON = "{\"description\":\"Honda Civic LX 1998\"}";

        given()
                .contentType(ContentType.JSON)
                .body(createVehicleJSON)
                .post("/vehicle/")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("vehicle", equalTo("Honda Civic LX 1998"));
    }
}
