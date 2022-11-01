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
public class ServiceControllerTestIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateService() {
        String createServiceJSON = "{" +
                "\"scheduling\":\"2022-01-01\";" +
                "\"description\":\"Troca de óleo\";" +
                "\"orderService\":\"dropbox.com/os001.pdf\";" +
                "\"idVehicle\":\"1\";" +
                "\"idWorkshop\":\"1\"}";

        given()
                .contentType(ContentType.JSON)
                .body(createServiceJSON)
                .post("/service/")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("scheduling", equalTo("2022-01-01"))
                .body("description", equalTo("Troca de óleo"))
                .body("orderService", equalTo("dropbox.com/os001.pdf"))
                .body("idVehicle", equalTo("1"))
                .body("idWorkshop", equalTo("1"));
    }
}