package com.sistema.ventas.app.domain.agents.ports.in;

import com.sistema.ventas.app.domain.agents.models.Agent;

public interface ICreateAgentUseCase {
    void execute(Agent agent);
}
