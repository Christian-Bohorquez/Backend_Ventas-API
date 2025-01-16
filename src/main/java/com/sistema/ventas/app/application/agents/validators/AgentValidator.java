package com.sistema.ventas.app.application.agents.validators;

import com.sistema.ventas.app.domain.accounts.ports.out.IAccountRepositoryPort;
import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.domain.persons.port.out.IPersonRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import org.springframework.stereotype.Component;

@Component
public class AgentValidator {

    private final IPersonRepositoryPort _personRepository;
    private final IAccountRepositoryPort _accountRepository;

    public AgentValidator(
        IPersonRepositoryPort personRepository,
        IAccountRepositoryPort accountRepository )
    {
        this._personRepository = personRepository;
        this._accountRepository = accountRepository;
    }

    public void validateCreate(Agent agent) {
        if(_personRepository.existsByIdentification(agent.getIdentification().getValue())) {
            throw new UniqueConstraintViolationException("identificación");
        }

        if(_accountRepository.existsByUsername(agent.getUsername().getValue())) {
            throw new UniqueConstraintViolationException("nombre de usuario");
        }
    }

}
