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
public class WorkshopControllerTestIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateUser() {
        String createWorkshopJSON = "{\"workshop\":\"Oficina do Tião\";\"contact\":\"(11) 98888-9999)\"}";

        given()
                .contentType(ContentType.JSON)
                .body(createWorkshopJSON)
                .post("/workshop/")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("workshop", equalTo("Oficina do Tião"))
                .body("contact", equalTo("(11) 98888-9999)"));
    }
}
