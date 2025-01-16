package com.sistema.ventas.app.application.roles.validators;

import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.domain.roles.ports.out.IRoleRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import org.springframework.stereotype.Component;

@Component
public class RoleValidator {

    private final IRoleRepositoryPort _repository;

    public RoleValidator(IRoleRepositoryPort repository)
    {
        this._repository = repository;
    }

    public void validateCreate(Role role) {
        _repository.getByName(role.getName())
            .ifPresent((existingName -> {
                throw new UniqueConstraintViolationException("El nombre");
            }));
    }

}
