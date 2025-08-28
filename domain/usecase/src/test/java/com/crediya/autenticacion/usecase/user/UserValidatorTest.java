package com.crediya.autenticacion.usecase.user;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.usecase.exceptions.DomainValidationException;
import com.crediya.autenticacion.usecase.usersignup.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

        private User.UserBuilder validUserBuilder;

        @BeforeEach
        void setUp() {
            validUserBuilder = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .identityDocument("12345678")
                    .email("john.doe@example.com")
                    .baseSalary(new BigDecimal("50000"));
        }

        @Test
        @DisplayName("Should not throw exception for a valid user")
        void shouldPassForValidUser() {
            assertDoesNotThrow(() -> UserValidator.validateForCreation(validUserBuilder.build()));
        }

        @Test
        @DisplayName("Should throw exception for a null user")
        void shouldThrowExceptionForNullUser() {
            DomainValidationException exception = assertThrows(DomainValidationException.class, () -> {
                UserValidator.validateForCreation(null);
            });
            assertEquals("El usuario no puede ser nulo.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception for blank first name")
        void shouldThrowExceptionForBlankFirstName() {
            User user = validUserBuilder.firstName(" ").build();
            DomainValidationException exception = assertThrows(DomainValidationException.class, () -> {
                UserValidator.validateForCreation(user);
            });
            assertEquals("El nombre no puede ser nulo o vacío.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception for invalid email")
        void shouldThrowExceptionForInvalidEmail() {
            User user = validUserBuilder.email("invalid-email").build();
            DomainValidationException exception = assertThrows(DomainValidationException.class, () -> {
                UserValidator.validateForCreation(user);
            });
            assertEquals("El formato del correo electrónico no es válido.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception for negative salary")
        void shouldThrowExceptionForNegativeSalary() {
            User user = validUserBuilder.baseSalary(new BigDecimal("-100")).build();
            DomainValidationException exception = assertThrows(DomainValidationException.class, () -> {
                UserValidator.validateForCreation(user);
            });
            assertEquals("El salario base debe estar entre 0 y 15,000,000.", exception.getMessage());
        }
    }
