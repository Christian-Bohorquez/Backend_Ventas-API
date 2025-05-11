package com.sistema.ventas.app.application.agents.usecases;

import com.sistema.ventas.app.application.agents.validators.AgentValidator;
import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.domain.agents.ports.out.IAgentRepositoryPort;
import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.domain.roles.ports.out.IRoleRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateAgentUseCaseImplTest {

    private IAgentRepositoryPort agentRepository;
    private IRoleRepositoryPort roleRepository;
    private AgentValidator validator;
    private CreateAgentUseCaseImpl createAgentUseCase;

    @BeforeEach
    void setUp() {
        agentRepository = Mockito.mock(IAgentRepositoryPort.class);
        roleRepository = Mockito.mock(IRoleRepositoryPort.class);
        validator = Mockito.mock(AgentValidator.class);
        createAgentUseCase = new CreateAgentUseCaseImpl(agentRepository, roleRepository, validator);
    }

    @Test
    void execute_ShouldThrowException_WhenRoleNotFound() {
        // Arrange
        Agent agent = Agent.create(null, "0942673971", "John", "Doe", "Fren123");
        Mockito.when(roleRepository.getByName("Agente")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> createAgentUseCase.execute(agent));
    }

    @Test
    void execute_ShouldSaveAgent_WhenValid() {
        // Arrange
        Agent agent = Agent.create(null, "0942673971", "John", "Doe", "Genis123");
        Role role = new Role(UUID.randomUUID(), "Agente");

        Mockito.when(roleRepository.getByName("Agente")).thenReturn(Optional.of(role));

        // Act
        createAgentUseCase.execute(agent);

        // Assert
        Mockito.verify(agentRepository).save(agent);
    }
}
