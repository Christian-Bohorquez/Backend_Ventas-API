package com.sistema.ventas.app.domain.roles.ports.out;

import com.sistema.ventas.app.domain.roles.models.Role;

import java.util.Optional;

public interface IRoleRepositoryPort {
    void save(Role role);
    Optional<Role> getByName(String name);
}
