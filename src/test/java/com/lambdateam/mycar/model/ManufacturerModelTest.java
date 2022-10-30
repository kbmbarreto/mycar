package com.lambdateam.mycar.model;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerModelTest {

    private ManufacturerModel manufacturerModel;

    @Before
    public void setUp() throws Exception {
        manufacturerModel = new ManufacturerModel( 1L, "Honda");
    }

    @After
    public void tearDown() throws Exception {
        System.out.print("Test completed");
    }

    @Test
    void getManufacturerTest() throws Exception {
        setUp();
        assertEquals(1L, manufacturerModel.getId());
        assertEquals("Honda", manufacturerModel.getManufacturer());
    }

    @Test
    void ManufacturerHashCodeTest() {
        ManufacturerModel x = new ManufacturerModel(1L, "Honda");
        assertEquals(x, x);
    }
}