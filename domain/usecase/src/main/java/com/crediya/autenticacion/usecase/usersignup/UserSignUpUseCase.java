package com.crediya.autenticacion.usecase.usersignup;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.PasswordManager;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.exceptions.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserSignUpUseCase {

    private final UserRepository userRepository;
    private final PasswordManager passwordManager;

    public Mono<User> processUserSignUp(User user) {

        try {
            UserValidator.validateForCreation(user);
        } catch (Exception e) {
            return Mono.error(e);
        }

        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new EmailAlreadyExistsException(
                                "El correo " + user.getEmail() + " ya está registrado."
                        ));
                    }
                    String hashedPassword = passwordManager.encode(user.getPassword());
                    user.setPassword(hashedPassword);

                    return userRepository.save(user);
                });
    }
}
