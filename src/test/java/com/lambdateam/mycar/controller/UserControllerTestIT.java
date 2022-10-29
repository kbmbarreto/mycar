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
public class UserControllerTestIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateUser() {
        String createUserJSON = "{\"email\":\"teste@teste.com\";\"userName\":\"kleber.barreto\";\"password\":\"Teste@123\"}";

        given()
                .contentType(ContentType.JSON)
                .body(createUserJSON)
                .post("/user")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("userName", equalTo("kleber.barreto"))
                .body("email", equalTo("teste@teste.com"))
                .body("password", equalTo("Teste@123"));
    }
}
