package com.crediya.autenticacion.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserDetailResponse {
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal baseSalary;
}
