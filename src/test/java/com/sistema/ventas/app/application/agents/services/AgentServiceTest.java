package com.sistema.ventas.app.application.agents.services;

import com.sistema.ventas.app.application.agents.dtos.AgentCreateDto;
import com.sistema.ventas.app.application.agents.mappers.AgentMapperApp;
import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.domain.agents.ports.in.ICreateAgentUseCase;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgentServiceTest {

    private AgentMapperApp mapperMock;
    private ICreateAgentUseCase createAgentUseCaseMock;
    private AgentService agentService;

    @BeforeEach
    void setUp() {
        mapperMock = mock(AgentMapperApp.class);
        createAgentUseCaseMock = mock(ICreateAgentUseCase.class);
        agentService = new AgentService(mapperMock, createAgentUseCaseMock);
    }

    @Test
    void createClient_ValidDto_ReturnsSuccessResponse() throws Exception {
        // GIVEN
        AgentCreateDto dto = new AgentCreateDto();

        // Usamos reflexión para asignar valores a los campos privados de AgentCreateDto
        Field identificationField = AgentCreateDto.class.getDeclaredField("identification");
        identificationField.setAccessible(true);
        identificationField.set(dto, "0942673971");

        Field firstNameField = AgentCreateDto.class.getDeclaredField("firstName");
        firstNameField.setAccessible(true);
        firstNameField.set(dto, "John");

        Field lastNameField = AgentCreateDto.class.getDeclaredField("lastName");
        lastNameField.setAccessible(true);
        lastNameField.set(dto, "Doe");

        Field usernameField = AgentCreateDto.class.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(dto, "Dnisse123");

        Field passwordField = AgentCreateDto.class.getDeclaredField("password");
        passwordField.setAccessible(true);
        passwordField.set(dto, "password123");

        // Creamos un mock de Agent (no se puede instanciar directamente)
        Agent agentMock = mock(Agent.class);

        // Mock del mapeo y ejecución del caso de uso
        when(mapperMock.toAgent(dto)).thenReturn(agentMock);
        doNothing().when(createAgentUseCaseMock).execute(agentMock);

        // WHEN
        ResponseAPI response = agentService.createClient(dto);

        // THEN
        assertNotNull(response, "La respuesta no debe ser null");
        assertEquals(201, response.getStatusCode(), "El código de respuesta debe ser 201");
        assertEquals("Agente creado exitosamente", response.getMessage(), "El mensaje de respuesta debe ser correcto");

        // Verificar que los métodos mockeados fueron llamados correctamente
        verify(mapperMock, times(1)).toAgent(dto);
        verify(createAgentUseCaseMock, times(1)).execute(agentMock);
    }
    @Test
    void createClient_InvalidDto_ThrowsException() {
        // GIVEN
        AgentCreateDto dto = null;  // Simula un DTO nulo

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> agentService.createClient(dto),
                "Se esperaba que el servicio lanzara una excepción por DTO nulo");

    }


    @Test
    void createClient_ValidationFails_ThrowsUniqueConstraintViolationException() throws Exception {
        // GIVEN
        AgentCreateDto dto = new AgentCreateDto();

        // Usamos reflexión para asignar valores a los campos privados de AgentCreateDto
        Field identificationField = AgentCreateDto.class.getDeclaredField("identification");
        identificationField.setAccessible(true);
        identificationField.set(dto, "0942673971");

        Field usernameField = AgentCreateDto.class.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(dto, "Dnisse123");

        // Simulamos una violación de constraint con una excepción personalizada
        doThrow(new UniqueConstraintViolationException("identificación"))
                .when(createAgentUseCaseMock).execute(any(Agent.class));

        // WHEN & THEN
        assertThrows(UniqueConstraintViolationException.class, () -> agentService.createClient(dto),
                "Se esperaba que el servicio lanzara una excepción de constraint violada");
    }


    @Test
    void createClient_FailedToMapDtoToAgent_ThrowsException() throws Exception {
        // GIVEN
        AgentCreateDto dto = new AgentCreateDto();
        when(mapperMock.toAgent(dto)).thenThrow(new NullPointerException("DTO es nulo"));

        // WHEN & THEN
        assertThrows(NullPointerException.class, () -> agentService.createClient(dto),
                "Se esperaba una excepción por fallo al mapear el DTO");
    }

    @Test
    void createClient_CreateAgentUseCaseFails_ThrowsException() throws Exception {
        // GIVEN
        AgentCreateDto dto = new AgentCreateDto();

        // Usamos reflexión para asignar valores a los campos privados de AgentCreateDto
        Field identificationField = AgentCreateDto.class.getDeclaredField("identification");
        identificationField.setAccessible(true);
        identificationField.set(dto, "0942673971");

        Field firstNameField = AgentCreateDto.class.getDeclaredField("firstName");
        firstNameField.setAccessible(true);
        firstNameField.set(dto, "John");

        Field lastNameField = AgentCreateDto.class.getDeclaredField("lastName");
        lastNameField.setAccessible(true);
        lastNameField.set(dto, "Doe");

        Field usernameField = AgentCreateDto.class.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(dto, "Dnisse123");

        Field passwordField = AgentCreateDto.class.getDeclaredField("password");
        passwordField.setAccessible(true);
        passwordField.set(dto, "password123");

        // Simulamos un fallo en la ejecución del caso de uso
        doThrow(new RuntimeException("Error al guardar el agente")).when(createAgentUseCaseMock).execute(any(Agent.class));

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> agentService.createClient(dto),
                "Se esperaba una excepción al intentar crear un agente");
    }

}
