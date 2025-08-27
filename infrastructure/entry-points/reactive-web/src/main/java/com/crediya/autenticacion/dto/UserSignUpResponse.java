package com.crediya.autenticacion.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String message;
}
