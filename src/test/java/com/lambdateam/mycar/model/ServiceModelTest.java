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

public class ServiceModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testServiceModelValidation_Success() {
        // Arrange
        ServiceModel service = new ServiceModel();
        service.setScheduling(new Date(2021, 1, 1));
        service.setDescription("Regular maintenance");
        service.setVehicle(new VehicleModel());
        service.setWorkshop(new WorkshopModel());

        // Act
        Set<ConstraintViolation<ServiceModel>> violations = validator.validate(service);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }
}