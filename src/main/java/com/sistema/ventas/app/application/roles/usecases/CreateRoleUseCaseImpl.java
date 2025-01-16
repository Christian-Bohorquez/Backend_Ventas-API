package com.sistema.ventas.app.application.roles.usecases;

import com.sistema.ventas.app.application.agents.validators.AgentValidator;
import com.sistema.ventas.app.application.roles.validators.RoleValidator;
import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.domain.agents.ports.out.IAgentRepositoryPort;
import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.domain.roles.ports.in.ICreateRoleUseCase;
import com.sistema.ventas.app.domain.roles.ports.out.IRoleRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CreateRoleUseCaseImpl implements ICreateRoleUseCase {

    private final IRoleRepositoryPort _repository;
    private final RoleValidator _validator;

    public CreateRoleUseCaseImpl(
            IRoleRepositoryPort repository,
            RoleValidator validator )
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public void execute(Role role) {
        _validator.validateCreate(role);
        _repository.save(role);
    }

}
