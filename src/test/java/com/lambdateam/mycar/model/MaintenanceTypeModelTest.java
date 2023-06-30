package com.lambdateam.mycar.model;

import com.lambdateam.mycar.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class MaintenanceTypeModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testMaintenanceTypeModelValidation_Success() {
        // Arrange
        MaintenanceTypeModel maintenanceType = new MaintenanceTypeModel();
        maintenanceType.setMaintenanceType("Oil Change");

        // Act
        Set<ConstraintViolation<MaintenanceTypeModel>> violations = validator.validate(maintenanceType);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }
}