package com.crediya.autenticacion.api;

import com.crediya.autenticacion.dto.LoginRequest;
import com.crediya.autenticacion.dto.LoginResponse;
import com.crediya.autenticacion.usecase.login.LoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@Tag(name = "Authentication Management", description = "Endpoints para el inicio de sesión")
public class AuthController {

    private final LoginUseCase loginUseCase;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión de usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
            })
    public Mono<ResponseEntity<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Intento de inicio de sesión para el correo: {}", request.getEmail());
        return loginUseCase.login(request.getEmail(), request.getPassword())
                .map(token -> ResponseEntity.ok(new LoginResponse(token)))
                .doOnError(error -> log.warn("Fallo el inicio de sesión para {}: {}", request.getEmail(), error.getMessage()));
    }
}
