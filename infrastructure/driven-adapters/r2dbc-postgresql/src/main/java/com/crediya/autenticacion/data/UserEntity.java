package com.crediya.autenticacion.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Table("users")
public class UserEntity {
    @Id
    private Long id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("birth_date")
    private LocalDate birthDate;

    @Column("identity_document")
    private String identityDocument;

    private String address;
    private String phone;
    private String email;

    @Column("base_salary")
    private BigDecimal baseSalary;

    private String password;

    @Column("role_id")
    private Long roleId;
}
