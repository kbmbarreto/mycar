package com.lambdateam.mycar.model;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkshopModelTest {

    private WorkshopModel workshopModel;

    @Before
    public void setUp() throws Exception {
        workshopModel = new WorkshopModel( 1L, "Oficina do Tião", "tiao@bol.com");
    }

    @After
    public void tearDown() throws Exception {
        System.out.print("Test completed");
    }

    @Test
    void getWorkshopTest() throws Exception {
        setUp();
        assertEquals(1L, workshopModel.getId());
        assertEquals("Oficina do Tião", workshopModel.getWorkshop());
        assertEquals("tiao@bol.com", workshopModel.getContact());
    }

    @Test
    void WorkshopHashCodeTest() {
        WorkshopModel x = new WorkshopModel(1L, "Oficina do Tião", "tiao@bol.com");
        assertEquals(x, x);
    }
}