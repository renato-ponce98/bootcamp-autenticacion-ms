package com.crediya.autenticacion.api;

import com.crediya.autenticacion.dto.ErrorResponse;
import com.crediya.autenticacion.dto.UserDetailResponse;
import com.crediya.autenticacion.dto.UserSignUpRequest;
import com.crediya.autenticacion.dto.UserSignUpResponse;
import com.crediya.autenticacion.mapper.UserRestMapper;
import com.crediya.autenticacion.usecase.finduserbyidentitydocument.FindUserByIdentityDocumentUseCase;
import com.crediya.autenticacion.usecase.getuserdetail.GetUserDetailUseCase;
import com.crediya.autenticacion.usecase.usersignup.UserSignUpUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints para la gestión de usuarios")
public class UserSignUpController {

    private final UserSignUpUseCase userSignUpUseCase;
    private final FindUserByIdentityDocumentUseCase findUserUseCase;
    private final GetUserDetailUseCase getUserDetailUseCase;
    private final UserRestMapper userRestMapper;
    private final TransactionalOperator transactionalOperator;

    @PostMapping(path = "/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'ASESOR')")
    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema con sus datos personales básicos.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario registrado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSignUpResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "El correo electrónico ya se encuentra registrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )

    public Mono<UserSignUpResponse> signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a registrar", required = true, content = @Content(schema = @Schema(implementation = UserSignUpRequest.class)))
            @Valid @RequestBody UserSignUpRequest request) {

        log.info("Iniciando proceso de registro para el correo: {}", request.getEmail());

        Mono<UserSignUpResponse> businessFlow = Mono.just(request)
                .map(userRestMapper::toDomain)
                .flatMap(userSignUpUseCase::processUserSignUp)
                .map(userRestMapper::toResponse);

        return businessFlow.as(transactionalOperator::transactional)
                .doOnSuccess(response -> log.info("Usuario registrado exitosamente con ID: {}", response.getId()))
                .doOnError(error -> log.error("Error durante el registro para el correo {}: {}", request.getEmail(), error.getMessage()));
    }

    @GetMapping("/usuarios/documento/{identityDocument}")
    @Operation(
            summary = "Verificar si un usuario existe por documento de identidad",
            description = "Comprueba si ya existe un usuario registrado con el documento de identidad proporcionado. Requiere rol de ADMIN o ASESOR.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Devuelve 'true' si el usuario existe, 'false' en caso contrario."
                    )
            }
    )
    public Mono<ResponseEntity<Boolean>> userExists(@PathVariable("identityDocument") String identityDocument) {
        log.info("Verificando existencia del documento: {}", identityDocument);
        return findUserUseCase.userExists(identityDocument)
                .map(exists -> ResponseEntity.ok(exists));
    }

    @GetMapping("/usuarios/detalles/{identityDocument}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ASESOR')")
    public Mono<ResponseEntity<UserDetailResponse>> getUserDetails(@PathVariable("identityDocument") String identityDocument) {
        log.info("Buscando detalles para el documento: {}", identityDocument);
        return getUserDetailUseCase.findByIdentityDocument(identityDocument)
                .map(userRestMapper::toDetailResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
