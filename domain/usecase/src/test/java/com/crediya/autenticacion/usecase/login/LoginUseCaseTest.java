package com.crediya.autenticacion.usecase.login;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.PasswordManager;
import com.crediya.autenticacion.model.user.gateways.TokenProvider;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordManager passwordManager;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setIdentityDocument("12345678");
        testUser.setEmail("test@example.com");
        testUser.setPassword("hashedPassword");
        testUser.setRoleId(1L);

        testRole = new Role();
        testRole.setId(1L);
        testRole.setName("ROLE_CLIENTE");
    }

    @Test
    void shouldReturnTokenWhenCredentialsAreValid() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(testUser));
        when(passwordManager.matches(anyString(), anyString())).thenReturn(true);
        when(roleRepository.findById(any(Long.class))).thenReturn(Mono.just(testRole));
        when(tokenProvider.generateToken(any(User.class), any(Role.class))).thenReturn("jwt-token-valido");

        Mono<String> result = loginUseCase.login("test@example.com", "password123");

        StepVerifier.create(result)
                .expectNext("jwt-token-valido")
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());

        Mono<String> result = loginUseCase.login("no-existe@example.com", "password123");

        StepVerifier.create(result)
                .expectError(InvalidCredentialsException.class)
                .verify();
    }

    @Test
    void shouldReturnErrorWhenPasswordIsIncorrect() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(testUser));
        when(passwordManager.matches(anyString(), anyString())).thenReturn(false);

        Mono<String> result = loginUseCase.login("test@example.com", "password-incorrecta");

        StepVerifier.create(result)
                .expectError(InvalidCredentialsException.class)
                .verify();
    }
}
