package com.lambdateam.mycar.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class MaintenancesModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testMaintenancesModelValidation_Success() {
        // Arrange
        MaintenancesModel maintenance = new MaintenancesModel();
        maintenance.setKm(10000);
        maintenance.setMaintenanceDate(new Date(System.currentTimeMillis()));

        // Creating and setting related entities
        ManufacturerModel manufacturer = new ManufacturerModel();
        VehicleModel vehicle = new VehicleModel();
        ComponentModel component = new ComponentModel();
        MaintenanceTypeModel maintenanceType = new MaintenanceTypeModel();

        maintenance.setManufacturer(manufacturer);
        maintenance.setVehicle(vehicle);
        maintenance.setComponent(component);
        maintenance.setMaintenanceType(maintenanceType);

        // Act
        Set<ConstraintViolation<MaintenancesModel>> violations = validator.validate(maintenance);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }
}