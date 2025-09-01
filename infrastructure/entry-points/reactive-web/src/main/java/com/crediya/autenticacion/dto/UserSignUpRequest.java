package com.crediya.autenticacion.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserSignUpRequest {

    @NotBlank(message = "El nombre no puede estar vacío.")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío.")
    private String lastName;

    @NotBlank(message = "El documento de identidad no puede estar vacío.")
    private String identityDocument;

    private LocalDate birthDate;

    private String address;

    private String phone;

    @NotBlank(message = "El correo electrónico no puede estar vacío.")
    @Email(message = "El formato del correo electrónico no es válido.")
    private String email;

    @NotNull(message = "El salario base no puede ser nulo.")
    @DecimalMin(value = "0.0", inclusive = true, message = "El salario base no puede ser negativo.")
    @DecimalMax(value = "15000000.0", inclusive = true, message = "El salario base no puede exceder 15,000,000.")
    private BigDecimal baseSalary;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String password;

    @NotNull(message = "El ID del rol no puede ser nulo.")
    @Positive(message = "El ID del rol debe ser un número positivo.")
    private Long roleId;
}
