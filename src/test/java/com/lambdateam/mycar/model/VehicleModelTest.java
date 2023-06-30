package com.lambdateam.mycar.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class VehicleModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testVehicleModelValidation_Success() {
        // Arrange
        VehicleModel vehicle = new VehicleModel();
        vehicle.setDescription("Car");

        // Act
        Set<ConstraintViolation<VehicleModel>> violations = validator.validate(vehicle);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void testVehicleModelValidation_Failure() {
        // Arrange
        VehicleModel vehicle = new VehicleModel();
        // vehicle.setDescription(null); // Uncomment this line to test the failure case

        // Act
        Set<ConstraintViolation<VehicleModel>> violations = validator.validate(vehicle);

        // Assert
        Assertions.assertEquals(1, violations.size());
        ConstraintViolation<VehicleModel> violation = violations.iterator().next();
        Assertions.assertEquals("Description is required", violation.getMessage());
    }
}