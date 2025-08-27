package com.crediya.autenticacion.mapper;

import com.crediya.autenticacion.dto.UserSignUpRequest;
import com.crediya.autenticacion.dto.UserSignUpResponse;
import com.crediya.autenticacion.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
    @Mapping(target = "id", ignore = true)
    User toDomain(UserSignUpRequest request);

    @Mapping(target = "message", constant = "Usuario registrado exitosamente.")
    UserSignUpResponse toResponse(User user);
}
