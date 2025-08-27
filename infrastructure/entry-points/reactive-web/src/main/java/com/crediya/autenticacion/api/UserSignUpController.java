package com.crediya.autenticacion.api;

import com.crediya.autenticacion.dto.UserSignUpRequest;
import com.crediya.autenticacion.dto.UserSignUpResponse;
import com.crediya.autenticacion.mapper.UserRestMapper;
import com.crediya.autenticacion.usecase.usersignup.UserSignUpUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserSignUpController {

    private final UserSignUpUseCase userSignUpUseCase;
    private final UserRestMapper userRestMapper;
    private final TransactionalOperator transactionalOperator;

    @PostMapping(path = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserSignUpResponse> signUp(@Valid @RequestBody UserSignUpRequest request) {
        log.info("Iniciando proceso de registro para el correo: {}", request.getEmail());

        Mono<UserSignUpResponse> businessFlow = Mono.just(request)
                .map(userRestMapper::toDomain)
                .flatMap(userSignUpUseCase::processUserSignUp)
                .map(userRestMapper::toResponse);

        return businessFlow.as(transactionalOperator::transactional)
                .doOnSuccess(response -> log.info("Usuario registrado exitosamente con ID: {}", response.getId()))
                .doOnError(error -> log.error("Error durante el registro para el correo {}: {}", request.getEmail(), error.getMessage()));
    }
}
