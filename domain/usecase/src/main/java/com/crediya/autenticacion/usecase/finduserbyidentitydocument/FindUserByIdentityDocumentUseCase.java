package com.crediya.autenticacion.usecase.finduserbyidentitydocument;

import com.crediya.autenticacion.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindUserByIdentityDocumentUseCase {
    private final UserRepository userRepository;

    public Mono<Boolean> userExists(String identityDocument) {
        return userRepository.existsByIdentityDocument(identityDocument);
    }
}
