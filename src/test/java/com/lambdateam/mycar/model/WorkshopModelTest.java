package com.lambdateam.mycar.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class WorkshopModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testWorkshopModelValidation_Success() {
        // Arrange
        WorkshopModel workshop = new WorkshopModel();
        workshop.setWorkshop("Auto Workshop");

        // Act
        Set<ConstraintViolation<WorkshopModel>> violations = validator.validate(workshop);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void testWorkshopModelValidation_Failure() {
        // Arrange
        WorkshopModel workshop = new WorkshopModel();
        // workshop.setWorkshop(null); // Uncomment this line to test the failure case

        // Act
        Set<ConstraintViolation<WorkshopModel>> violations = validator.validate(workshop);

        // Assert
        Assertions.assertEquals(1, violations.size());
        ConstraintViolation<WorkshopModel> violation = violations.iterator().next();
        Assertions.assertEquals("Workshop is required", violation.getMessage());
    }
}
