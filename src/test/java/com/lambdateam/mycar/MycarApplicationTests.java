package com.lambdateam.mycar;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MycarApplicationTests {

    @Test
    @Description("Teste exemplo 1")
    void testOperation() {
        int result = 2 + 2;
        assertEquals(4, result);
    }

    @Test
    @Description("Teste exemplo 2")
    void testOperation2() {
        int result = 2 + 2;
        assertEquals(4, result);
    }

    @Test
    @Description("Teste exemplo 3")
    void testOperatio3() {
        int result = 2 + 2;
        assertEquals(4, result);
    }

}