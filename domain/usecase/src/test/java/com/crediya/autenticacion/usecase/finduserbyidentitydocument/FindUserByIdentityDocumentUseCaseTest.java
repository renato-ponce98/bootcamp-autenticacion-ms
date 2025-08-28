package com.crediya.autenticacion.usecase.finduserbyidentitydocument;

import com.crediya.autenticacion.model.user.gateways.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FindUserByIdentityDocumentUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByIdentityDocumentUseCase useCase;

    @Test
    @DisplayName("Should return true when user exists")
    void shouldReturnTrueWhenUserExists() {
        when(userRepository.existsByIdentityDocument(anyString())).thenReturn(Mono.just(true));

        Mono<Boolean> result = useCase.userExists("12345");

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return false when user does not exist")
    void shouldReturnFalseWhenUserDoesNotExist() {
        when(userRepository.existsByIdentityDocument(anyString())).thenReturn(Mono.just(false));

        Mono<Boolean> result = useCase.userExists("12345");

        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }
}
