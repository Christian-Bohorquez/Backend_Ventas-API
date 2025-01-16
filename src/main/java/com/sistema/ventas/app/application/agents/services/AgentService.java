package com.sistema.ventas.app.application.agents.services;

import com.sistema.ventas.app.application.agents.dtos.AgentCreateDto;
import com.sistema.ventas.app.application.agents.mappers.AgentMapperApp;
import com.sistema.ventas.app.domain.agents.ports.in.ICreateAgentUseCase;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    private final AgentMapperApp _mapper;
    private final ICreateAgentUseCase _createAgentUseCase;

    public AgentService(
            AgentMapperApp mapper,
            ICreateAgentUseCase createAgentUseCase )
    {
        this._mapper = mapper;
        this._createAgentUseCase = createAgentUseCase;
    }

    public ResponseAPI createClient(AgentCreateDto dto) {
        var client = _mapper.toAgent(dto);
        _createAgentUseCase.execute(client);
        return new ResponseAPI(201, "Agente creado exitosamente");
    }

}
