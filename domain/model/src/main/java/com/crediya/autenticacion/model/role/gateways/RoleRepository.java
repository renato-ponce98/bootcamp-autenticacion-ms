package com.crediya.autenticacion.model.role.gateways;


import com.crediya.autenticacion.model.role.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> findById(Long id);
}
