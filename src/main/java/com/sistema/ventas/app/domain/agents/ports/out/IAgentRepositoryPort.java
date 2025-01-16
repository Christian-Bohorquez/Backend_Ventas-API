package com.sistema.ventas.app.domain.agents.ports.out;

import com.sistema.ventas.app.domain.agents.models.Agent;

import java.util.Optional;

public interface IAgentRepositoryPort {
    void save(Agent entity);
}
