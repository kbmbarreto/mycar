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
public class ComponentControllerTestIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateComponent() {
        String createComponentJSON = "{\"component\":\"Filtro de óleo\"}";

        given()
                .contentType(ContentType.JSON)
                .body(createComponentJSON)
                .post("/component/")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("Component", equalTo("Filtro de óleo"));
    }
}
