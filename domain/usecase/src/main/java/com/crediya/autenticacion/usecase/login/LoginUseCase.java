package com.crediya.autenticacion.usecase.login;

import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import com.crediya.autenticacion.model.user.gateways.PasswordManager;
import com.crediya.autenticacion.model.user.gateways.TokenProvider;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.exceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordManager passwordManager;
    private final TokenProvider tokenProvider;

    public Mono<String> login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .flatMap(user -> {
                    if (passwordManager.matches(rawPassword, user.getPassword())) {
                        return roleRepository.findById(user.getRoleId())
                                .map(role -> tokenProvider.generateToken(user, role));
                    }
                    return Mono.error(new InvalidCredentialsException("Credenciales inválidas."));
                })
                .switchIfEmpty(Mono.error(new InvalidCredentialsException("Credenciales inválidas.")));
    }
}
