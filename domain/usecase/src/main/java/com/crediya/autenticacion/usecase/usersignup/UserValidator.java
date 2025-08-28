package com.crediya.autenticacion.usecase.usersignup;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.usecase.exceptions.DomainValidationException;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class UserValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public static void validateForCreation(User user) {
        if (user == null) {
            throw new DomainValidationException("El usuario no puede ser nulo.");
        }
        if (isNullOrBlank(user.getFirstName())) {
            throw new DomainValidationException("El nombre no puede ser nulo o vacío.");
        }
        if (isNullOrBlank(user.getLastName())) {
            throw new DomainValidationException("El apellido no puede ser nulo o vacío.");
        }
        if (isNullOrBlank(user.getIdentityDocument())) {
            throw new DomainValidationException("El documento de identidad no puede ser nulo o vacío.");
        }
        if (isNullOrBlank(user.getEmail()) || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new DomainValidationException("El formato del correo electrónico no es válido.");
        }
        if (user.getBaseSalary() == null || user.getBaseSalary().compareTo(BigDecimal.ZERO) < 0 || user.getBaseSalary().compareTo(new BigDecimal("15000000")) > 0) {
            throw new DomainValidationException("El salario base debe estar entre 0 y 15,000,000.");
        }
    }

    private static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
