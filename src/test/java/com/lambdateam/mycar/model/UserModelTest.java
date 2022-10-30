package com.lambdateam.mycar.model;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {
    private UserModel userModel;

    @Before
    public void setUp() throws Exception {
        userModel = new UserModel( 1L, "Teste", "teste@teste.com", "Teste@123");
    }

    @After
    public void tearDown() throws Exception {
        System.out.print("Test completed");
    }

    @Test
    void getUserTest() throws Exception {
        setUp();
        assertEquals(1L, userModel.getId());
        assertEquals("Teste", userModel.getUserName());
        assertEquals("teste@teste.com", userModel.getEmail());
        assertEquals("Teste@123", userModel.getPassword());
    }

    @Test
    void UserHashCodeTest() {
        UserModel x = new UserModel(1L, "Teste", "teste@teste.com", "Teste@123");
        assertEquals(x, x);
    }
}