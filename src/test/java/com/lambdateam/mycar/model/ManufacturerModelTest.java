package com.lambdateam.mycar.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ManufacturerModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testManufacturerModelValidation_Success() {
        // Arrange
        ManufacturerModel manufacturer = new ManufacturerModel();
        manufacturer.setManufacturer("Toyota");

        // Act
        Set<ConstraintViolation<ManufacturerModel>> violations = validator.validate(manufacturer);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }
}