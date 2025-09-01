package com.crediya.autenticacion.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoleR2dbcRepository extends ReactiveCrudRepository<RoleEntity, Long> {
    Mono<RoleEntity> findByName(String name);
}
