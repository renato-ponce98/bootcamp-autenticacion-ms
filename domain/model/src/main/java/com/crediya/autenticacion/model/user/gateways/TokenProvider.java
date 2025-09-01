package com.crediya.autenticacion.model.user.gateways;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;

public interface TokenProvider {
    String generateToken(User user, Role role);
}
