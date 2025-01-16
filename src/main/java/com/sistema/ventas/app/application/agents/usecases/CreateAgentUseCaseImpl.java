package com.sistema.ventas.app.application.agents.usecases;

import com.sistema.ventas.app.application.agents.validators.AgentValidator;
import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.domain.agents.ports.in.ICreateAgentUseCase;
import com.sistema.ventas.app.domain.agents.ports.out.IAgentRepositoryPort;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.domain.roles.ports.out.IRoleRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CreateAgentUseCaseImpl implements ICreateAgentUseCase {

    private final IAgentRepositoryPort _agentRepository;
    private final IRoleRepositoryPort _roleRepository;
    private final AgentValidator _validator;
    private static final String CLIENT_ROLE_NAME = "Agente";

    public CreateAgentUseCaseImpl(
            IAgentRepositoryPort clientRepository,
            IRoleRepositoryPort roleRepository,
            AgentValidator validator )
    {
        this._agentRepository = clientRepository;
        this._roleRepository = roleRepository;
        this._validator = validator;
    }

    @Override
    public void execute(Agent agent) {
        _validator.validateCreate(agent);

        Role role = _roleRepository.getByName(CLIENT_ROLE_NAME)
                .orElseThrow(() -> new NotFoundException("Rol - " + CLIENT_ROLE_NAME));

        agent.AssignRole(role.getId());
        _agentRepository.save(agent);
    }

}
