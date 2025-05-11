package com.sistema.ventas.app.application.agents.validators;

import com.sistema.ventas.app.domain.accounts.ports.out.IAccountRepositoryPort;
import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.domain.persons.port.out.IPersonRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgentValidatorTest {

    private IPersonRepositoryPort personRepository;
    private IAccountRepositoryPort accountRepository;
    private AgentValidator validator;

    @BeforeEach
    void setUp() {
        personRepository = Mockito.mock(IPersonRepositoryPort.class);
        accountRepository = Mockito.mock(IAccountRepositoryPort.class);
        validator = new AgentValidator(personRepository, accountRepository);
    }

    @Test
    void validateCreate_ShouldThrowException_WhenIdentificationExists() {
        // Arrange
        Agent agent = Agent.create(null, "0942673971", "Denisse", "Doe", "John123");
        Mockito.when(personRepository.existsByIdentification(agent.getIdentification().getValue()))
                .thenReturn(true);

        // Act & Assert
        assertThrows(UniqueConstraintViolationException.class, () -> validator.validateCreate(agent));
    }

    @Test
    void validateCreate_ShouldThrowException_WhenUsernameExists() {
        // Arrange
        Agent agent = Agent.create(null, "0942673971", "denisse", "Doe", "John123");
        Mockito.when(accountRepository.existsByUsername(agent.getUsername().getValue()))
                .thenReturn(true);

        // Act & Assert
        assertThrows(UniqueConstraintViolationException.class, () -> validator.validateCreate(agent));
    }
}
