package com.lambdateam.mycar.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class UserModelTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserModelValidation_Success() {
        // Arrange
        UserModel user = new UserModel("John Doe", "john.doe@example.com", "123456789");

        // Act
        Set<ConstraintViolation<UserModel>> violations = validator.validate(user);

        // Assert
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void testUserModelValidation_Failure() {
        // Arrange
        UserModel user = new UserModel();
        // user.setUser(null); // Uncomment this line to test the failure case
        // user.setEmail(null); // Uncomment this line to test the failure case

        // Act
        Set<ConstraintViolation<UserModel>> violations = validator.validate(user);

        // Assert
        Assertions.assertEquals(2, violations.size());
        for (ConstraintViolation<UserModel> violation : violations) {
            if (violation.getPropertyPath().toString().equals("user")) {
                Assertions.assertEquals("Name is required", violation.getMessage());
            } else if (violation.getPropertyPath().toString().equals("email")) {
                Assertions.assertEquals("E-mail is required", violation.getMessage());
            }
        }
    }
}
