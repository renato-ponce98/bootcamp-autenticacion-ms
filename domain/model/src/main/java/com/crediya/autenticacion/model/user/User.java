package com.crediya.autenticacion.model.user;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String identityDocument;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal baseSalary;
    private String password;
    private Long roleId;
}
