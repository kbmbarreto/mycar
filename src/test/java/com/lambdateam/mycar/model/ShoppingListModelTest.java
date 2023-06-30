package com.lambdateam.mycar.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ShoppingListModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testShoppingListModelValidation_Success() {
        // Arrange
        ShoppingListModel shoppingList = new ShoppingListModel();
        shoppingList.setNotes("Buy new tires");
        shoppingList.setFullfiled(true);
        shoppingList.setVehicle(new VehicleModel());
        shoppingList.setComponent(new ComponentModel());

        // Act
        Set<ConstraintViolation<ShoppingListModel>> violations = validator.validate(shoppingList);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }
}