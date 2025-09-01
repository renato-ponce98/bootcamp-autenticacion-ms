package com.crediya.autenticacion.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserR2dbcRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByIdentityDocument(String identityDocument);
    Mono<UserEntity> findByEmail(String email);
}
