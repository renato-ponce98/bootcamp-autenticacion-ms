package com.crediya.autenticacion.mapper;

import com.crediya.autenticacion.data.UserEntity;
import com.crediya.autenticacion.model.user.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User toDomain(UserEntity entity);

    @InheritInverseConfiguration
    UserEntity toEntity(User domain);
}
