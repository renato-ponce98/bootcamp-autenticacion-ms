package com.crediya.autenticacion.usecase.getuserdetail;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetUserDetailUseCase {

    private final UserRepository userRepository;

    public Mono<User> findByIdentityDocument(String identityDocument) {
        return userRepository.findByIdentityDocument(identityDocument);
    }
}
