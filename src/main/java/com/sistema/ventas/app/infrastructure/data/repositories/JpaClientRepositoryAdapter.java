package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import com.sistema.ventas.app.infrastructure.data.entities.ClientEntity;
import com.sistema.ventas.app.infrastructure.data.entities.PersonEntity;
import com.sistema.ventas.app.infrastructure.data.mappers.ClientMapperInfra;
import com.sistema.ventas.app.infrastructure.data.mappers.PersonMapperInfra;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaClientRepositoryAdapter implements IClientRepositoryPort {

    private final JpaClientRepository _clientRepository;
    private final JpaPersonRepository _personRepository;
    private final ClientMapperInfra _clientMapper;
    private final PersonMapperInfra _personMapper;

    public JpaClientRepositoryAdapter(
            JpaClientRepository clientRepository,
            JpaPersonRepository personRepository,
            ClientMapperInfra clientMapper,
            PersonMapperInfra personMapper
    ) {
        this._clientRepository = clientRepository;
        this._personRepository = personRepository;
        this._clientMapper = clientMapper;
        this._personMapper = personMapper;
    }

    @Transactional
    @Override
    public void save(Client entity) {
        Optional<PersonEntity> existingPerson = _personRepository.findById(entity.getId());
        if (existingPerson.isPresent()) {
            PersonEntity updatePersonEntity = _personMapper.toUpdateEntity(existingPerson.get(), entity);
            _personRepository.save(updatePersonEntity);
            Optional<ClientEntity> existingClient = _clientRepository.findByClientIdOrPersonId(entity.getId());
            if (existingClient.isPresent()) {
                ClientEntity clientEntity = _clientMapper.toUpdateEntity(existingClient.get(), entity);
                _clientRepository.save(clientEntity);
            }
        } else {
            PersonEntity newPerson = _personMapper.toCreateEntity(entity);
            _personRepository.save(newPerson);
            ClientEntity newClient = _clientMapper.toCreateEntity(entity);
            newClient.setPerson(newPerson);
            _clientRepository.save(newClient);
        }
    }

    @Override
    public void delete(UUID id) {
        _clientRepository.findByClientIdOrPersonId(id).ifPresent(client -> {
            client.setActive(false);
            client.setDeletedAt(LocalDateTime.now());
            _clientRepository.save(client);

            PersonEntity person = client.getPerson();
            if (person != null) {
                person.setActive(false);
                person.setDeletedAt(LocalDateTime.now());
                _personRepository.save(person);
            }
        });
    }

    @Override
    public Optional<Client> getById(UUID id) {
        return _clientRepository.findByClientIdOrPersonId(id)
            .filter(ClientEntity::isActive)
            .map(_clientMapper::toDomain);
    }

    @Override
    public Optional<Client> getByIdentification(String identification) {
        return _clientRepository.findClientByIdentificationWithoutRole(identification)
            .filter(ClientEntity::isActive)
            .map(_clientMapper::toDomain);
    }

    @Override
    public List<Client> getAll() {
        return _clientRepository.findAll().stream()
            .filter(ClientEntity::isActive)
            .map(_clientMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Client> getByEmail(String email) {
        return _clientRepository.findByEmailAndIsActiveTrue(email)
            .map(_clientMapper::toDomain);
    }

}
