package com.crediya.autenticacion;

import com.crediya.autenticacion.data.RoleR2dbcRepository;
import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapterImpl implements RoleRepository {
    private final RoleR2dbcRepository repository;
    @Override
    public Mono<Role> findById(Long id) {
        return repository.findById(id)
                .map(entity -> {
                    Role role = new Role();
                    role.setId(entity.getId());
                    role.setName(entity.getName());
                    return role;
                });
    }
}
