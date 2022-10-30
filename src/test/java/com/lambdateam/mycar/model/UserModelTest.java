package com.lambdateam.mycar.model;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }

    @Test
    void getUserName() {
    }

    @Test
    void setUserName() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void setEmail() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void setPassword() {
    }

    @Test
    void testHashCode() {
        UserModel x = new UserModel(1L, "Teste", "teste@teste.com", "Teste@123");
        assertEquals(x, x);
    }

    @Test
    void testEquals() {
    }

    //EXAMPLE:

    // @Test
    //public void testEquals_Symmetric() {
    //    Person x = new Person("Foo Bar");  // equals and hashCode check name field value
    //    Person y = new Person("Foo Bar");
    //    Assert.assertTrue(x.equals(y) && y.equals(x));
    //    Assert.assertTrue(x.hashCode() == y.hashCode());
    //}
}