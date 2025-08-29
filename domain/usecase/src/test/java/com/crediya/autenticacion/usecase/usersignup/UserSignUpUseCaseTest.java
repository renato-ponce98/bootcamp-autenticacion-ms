package com.crediya.autenticacion.usecase.usersignup;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.exceptions.DomainValidationException;
import com.crediya.autenticacion.usecase.exceptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSignUpUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSignUpUseCase userSignUpUseCase;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .identityDocument("12345678")
                .email("john.doe@example.com")
                .baseSalary(new BigDecimal("60000"))
                .build();
    }

    @Test
    @DisplayName("Should register user when email does not exist.")
    void shouldRegisterUserSuccessfullyWhenEmailDoesNotExist() {
        when(userRepository.existsByEmail(any(String.class))).thenReturn(Mono.just(false));

        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));


        Mono<User> result = userSignUpUseCase.processUserSignUp(testUser);


        StepVerifier.create(result)
                .expectNext(testUser)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should throw EmailAlreadyExistsException when email already exists.")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(any(String.class))).thenReturn(Mono.just(true));


        Mono<User> result = userSignUpUseCase.processUserSignUp(testUser);


        StepVerifier.create(result)
                .expectError(EmailAlreadyExistsException.class)
                .verify();
    }

    @Test
    @DisplayName("Should throw DomainValidationException for invalid user data")
    void shouldThrowDomainValidationExceptionForInvalidUserData() {
        User invalidUser = User.builder()
                .firstName("")
                .lastName("Doe")
                .identityDocument("12345678")
                .email("test@example.com")
                .baseSalary(new BigDecimal("1000"))
                .build();

        Mono<User> result = userSignUpUseCase.processUserSignUp(invalidUser);

        StepVerifier.create(result)
                .expectError(DomainValidationException.class)
                .verify();
    }
}
