package com.crediya.autenticacion.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class BcryptPasswordManagerAdapterTest {

    private BcryptPasswordManagerAdapter passwordManagerAdapter;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        passwordManagerAdapter = new BcryptPasswordManagerAdapter(passwordEncoder);
    }

    @Test
    void shouldEncodePasswordSuccessfully() {
        String rawPassword = "password123";

        String encodedPassword = passwordManagerAdapter.encode(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordManagerAdapter.matches(rawPassword, encodedPassword));
    }

    @Test
    void shouldReturnTrueWhenPasswordsMatch() {
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        boolean result = passwordManagerAdapter.matches(rawPassword, encodedPassword);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenPasswordsDoNotMatch() {
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        String wrongPassword = "password-incorrecta";

        boolean result = passwordManagerAdapter.matches(wrongPassword, encodedPassword);

        assertFalse(result);
    }
}
