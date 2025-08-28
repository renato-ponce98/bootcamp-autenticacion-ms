package com.crediya.autenticacion;

import com.crediya.autenticacion.data.UserR2dbcRepository;
import com.crediya.autenticacion.mapper.UserEntityMapper;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapterImpl implements UserRepository {

    private final UserR2dbcRepository repository;
    private final UserEntityMapper mapper;

    @Override
    public Mono<User> save(User user) {
        return Mono.just(user)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByIdentityDocument(String identityDocument) {
        return repository.existsByIdentityDocument(identityDocument);
    }
}
