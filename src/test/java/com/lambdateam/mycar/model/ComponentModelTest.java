package com.lambdateam.mycar.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ComponentModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testComponentModelValidation_Success() {
        // Arrange
        ComponentModel component = new ComponentModel();
        component.setComponent("Engine");

        // Act
        Set<ConstraintViolation<ComponentModel>> violations = validator.validate(component);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void testComponentModelValidation_Failure() {
        // Arrange
        ComponentModel component = new ComponentModel();
        // component.setComponent(null); // Uncomment this line to test the failure case

        // Act
        Set<ConstraintViolation<ComponentModel>> violations = validator.validate(component);

        // Assert
        Assertions.assertEquals(1, violations.size());
        ConstraintViolation<ComponentModel> violation = violations.iterator().next();
        Assertions.assertEquals("Component is required", violation.getMessage());
    }
}
