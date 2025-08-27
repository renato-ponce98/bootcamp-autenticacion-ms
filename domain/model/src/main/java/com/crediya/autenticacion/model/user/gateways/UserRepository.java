package com.crediya.autenticacion.model.user.gateways;

import com.crediya.autenticacion.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> save(User user);

    Mono<Boolean> existsByEmail(String email);

}
