package com.lambdateam.mycar.model;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComponentModelTest {

    private ComponentModel componentModel;

    @Before
    public void setUp() throws Exception {
        componentModel = new ComponentModel( 1L, "Filtro de óleo");
    }
    @After
    public void tearDown() throws Exception {
        System.out.print("Test completed");
    }

    @Test
    void getComponentTest() throws Exception {
        setUp();
        assertEquals(1L, componentModel.getId());
        assertEquals("Filtro de óleo", componentModel.getComponent());
    }

    @Test
    void ComponentHashCodeTest() {
        ComponentModel x = new ComponentModel(1L, "Filtro de óleo");
        assertEquals(x, x);
    }
}