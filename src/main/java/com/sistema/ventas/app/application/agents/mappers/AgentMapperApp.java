package com.sistema.ventas.app.application.agents.mappers;

import com.sistema.ventas.app.application.agents.dtos.AgentCreateDto;
import com.sistema.ventas.app.domain.agents.builders.AgentBuilder;
import com.sistema.ventas.app.domain.agents.models.Agent;
import org.springframework.stereotype.Component;

@Component
public class AgentMapperApp {

    public Agent toAgent(AgentCreateDto dto) {
        Agent agent = new AgentBuilder()
            .withIdentification(dto.getIdentification())
            .withFirstName(dto.getFirstName())
            .withLastName(dto.getLastName())
            .withUsername(dto.getUsername())
            .build();
        agent.AssignPassword(dto.getPassword());
        return agent;
    }

}
