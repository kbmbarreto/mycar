package com.lambdateam.mycar.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Date;
import java.util.Set;

public class TrafficTicketModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTrafficTicketModelValidation_Success() {
        // Arrange
        TrafficTicketModel trafficTicket = new TrafficTicketModel();
        trafficTicket.setDescription("Speeding violation");
        trafficTicket.setDate(new Date(System.currentTimeMillis()));
        trafficTicket.setNotes("Fine paid");
        trafficTicket.setVehicle(new VehicleModel());

        // Act
        Set<ConstraintViolation<TrafficTicketModel>> violations = validator.validate(trafficTicket);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }
}