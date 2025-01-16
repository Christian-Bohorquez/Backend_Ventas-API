package com.sistema.ventas.app.domain.roles.ports.in;

import com.sistema.ventas.app.domain.roles.models.Role;

public interface ICreateRoleUseCase {
    void execute(Role role);
}
