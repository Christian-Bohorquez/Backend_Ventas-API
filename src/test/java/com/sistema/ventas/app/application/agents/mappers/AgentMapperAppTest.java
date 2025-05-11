package com.sistema.ventas.app.application.agents.mappers;

import com.sistema.ventas.app.application.agents.dtos.AgentCreateDto;
import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.domain.agents.valueobjects.Username;
import com.sistema.ventas.app.domain.shared.valueobjects.Identification;
import com.sistema.ventas.app.domain.shared.valueobjects.Name;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AgentMapperAppTest {

    private final AgentMapperApp mapper = new AgentMapperApp();


    @Test
    void toAgent_NullAgentCreateDto_ThrowsException() {
        AgentCreateDto dto = null;

        try {
            assertThrows(
                    NullPointerException.class,
                    () -> mapper.toAgent(dto),
                    "Debería lanzar una excepción si el AgentCreateDto es null"
            );
            System.out.println("Prueba exitosa: Se lanzó la excepción esperada al pasar un AgentCreateDto null.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba toAgent_NullAgentCreateDto_ThrowsException: " + e.getMessage());
            throw e;
        }
    }

}
