package com.sistema.ventas.app.application.clients.validators;

import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import com.sistema.ventas.app.domain.persons.port.out.IPersonRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientValidator {

    private final IPersonRepositoryPort _personValidator;
    private final IClientRepositoryPort _clientRepository;

    public ClientValidator(
            IPersonRepositoryPort personValidator,
            IClientRepositoryPort clientRepository)
    {
        this._personValidator = personValidator;
        this._clientRepository = clientRepository;
    }

    public void validateCreate(Client client) {
        if(_personValidator.existsByIdentification(client.getIdentification().getValue())) {
            throw new UniqueConstraintViolationException("identificación");
        }

        _clientRepository.getByEmail(client.getEmail().getValue())
            .ifPresent(existingClient -> {
                throw new UniqueConstraintViolationException("El email");
            });
    }

    public void validateGetById(UUID id) {
        _clientRepository.getById(id)
            .orElseThrow(() -> new NotFoundException("Cliente"));
    }

    public void validateGetByIdentification(String identification) {
        if(!_personValidator.existsByIdentification(identification)) {
            throw new NotFoundException("cliente");
        }
    }

    public void validateUpdate(Client client) {
        _clientRepository.getById(client.getId())
            .orElseThrow(() -> new NotFoundException("Cliente"));

        _clientRepository.getByEmail(client.getEmail().getValue())
            .ifPresent(existingClient -> {
                if (!existingClient.getId().equals(client.getId())) {
                    throw new UniqueConstraintViolationException("El email");
                }
            });
    }
}
