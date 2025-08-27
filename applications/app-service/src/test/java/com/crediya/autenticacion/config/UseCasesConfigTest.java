package com.crediya.autenticacion.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@org.springframework.test.context.ContextConfiguration(classes = {UseCasesConfig.class, TestUseCaseConfig.class})
class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UseCasesConfig.class, TestUseCaseConfig.class)) {
            assertNotNull(context.getBean(com.crediya.autenticacion.usecase.usersignup.UserSignUpUseCase.class));
        }
    }
}