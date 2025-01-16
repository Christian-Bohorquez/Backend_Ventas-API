package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.domain.agents.ports.out.IAgentRepositoryPort;
import com.sistema.ventas.app.infrastructure.data.entities.ClientEntity;
import com.sistema.ventas.app.infrastructure.data.entities.PersonEntity;
import com.sistema.ventas.app.infrastructure.data.entities.RoleEntity;
import com.sistema.ventas.app.infrastructure.data.entities.UserEntity;
import com.sistema.ventas.app.infrastructure.data.mappers.AgentMapperInfra;
import com.sistema.ventas.app.infrastructure.data.mappers.PersonMapperInfra;
import com.sistema.ventas.app.infrastructure.data.mappers.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class JpaAgentRepositoryAdapter implements IAgentRepositoryPort {

    private final JpaPersonRepository _personRepository;
    private final JapAccountRepository _accountRepository;
    private final JpaRoleRepository _roleRepository;
    private final PersonMapperInfra _personMapper;
    private final UserMapper _userMapper;
    private final PasswordEncoder _passwordEncoder;

    public JpaAgentRepositoryAdapter(
        JpaPersonRepository personRepository,
        JapAccountRepository accountRepository,
        JpaRoleRepository roleRepository,
        PersonMapperInfra personMapper,
        UserMapper userMapper,
        PasswordEncoder passwordEncoder
    ) {
        this._personRepository = personRepository;
        this._accountRepository = accountRepository;
        this._roleRepository = roleRepository;
        this._personMapper = personMapper;
        this._userMapper = userMapper;
        this._passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void save(Agent entity) {
        Optional<PersonEntity> existingPerson = _personRepository.findById(entity.getId());
        Optional<RoleEntity> roleEntityOptional = _roleRepository.findById(entity.getRoleId());
        if (!existingPerson.isPresent() && roleEntityOptional.isPresent()) {
            PersonEntity newPerson = _personMapper.toCreateEntity(entity);
            _personRepository.save(newPerson);
            UserEntity newUser = _userMapper.toCreateEntity(entity);
            newUser.setPassword(_passwordEncoder.encode(newUser.getPassword()));
            RoleEntity roleEntity = roleEntityOptional.get();
            newUser.setPerson(newPerson);
            newUser.setRole(roleEntity);
            _accountRepository.save(newUser);
        }
    }
}
