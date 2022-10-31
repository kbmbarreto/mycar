package com.lambdateam.mycar.model;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleModelTest {

    private VehicleModel vehicleModel;

    @Before
    public void setUp() throws Exception {
        vehicleModel = new VehicleModel( 1L, "Honda Civic LX 1998");
    }

    @After
    public void tearDown() throws Exception {
        System.out.print("Test completed");
    }

    @Test
    void getVehicleTest() throws Exception {
        setUp();
        assertEquals(1L, vehicleModel.getId());
        assertEquals("Honda Civic LX 1998", vehicleModel.getDescription());
    }

    @Test
    void VehicleHashCodeTest() {
        VehicleModel x = new VehicleModel(1L, "Honda Civic LX 1998");
        assertEquals(x, x);
    }
}