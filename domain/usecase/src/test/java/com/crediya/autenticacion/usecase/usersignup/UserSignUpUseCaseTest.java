package com.crediya.autenticacion.usecase.usersignup;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.PasswordManager;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.exceptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSignUpUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordManager passwordManager;

    @InjectMocks
    private UserSignUpUseCase userSignUpUseCase;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .identityDocument("12345678")
                .email("john.doe@example.com")
                .password("password123")
                .baseSalary(BigDecimal.valueOf(50000))
                .roleId(2L)
                .build();
    }

    @Test
    void shouldSignUpUserSuccessfully() {
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(false));
        when(passwordManager.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));

        Mono<User> result = userSignUpUseCase.processUserSignUp(testUser);

        StepVerifier.create(result)
                .expectNext(testUser)
                .verifyComplete();

        verify(userRepository).existsByEmail("john.doe@example.com");
        verify(passwordManager).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldReturnErrorWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(true));

        Mono<User> result = userSignUpUseCase.processUserSignUp(testUser);

        StepVerifier.create(result)
                .expectError(EmailAlreadyExistsException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenDomainValidationFails() {
        User invalidUser = User.builder().lastName("Doe").build();

        Mono<User> result = userSignUpUseCase.processUserSignUp(invalidUser);

        StepVerifier.create(result)
                .expectError(com.crediya.autenticacion.usecase.exceptions.DomainValidationException.class)
                .verify();
    }
}
