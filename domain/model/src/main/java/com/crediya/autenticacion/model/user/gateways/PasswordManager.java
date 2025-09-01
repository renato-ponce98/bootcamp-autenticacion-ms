package com.crediya.autenticacion.model.user.gateways;

public interface PasswordManager {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
