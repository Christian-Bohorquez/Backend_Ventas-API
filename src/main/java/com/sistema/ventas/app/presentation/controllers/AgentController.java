package com.sistema.ventas.app.presentation.controllers;

import com.sistema.ventas.app.application.agents.dtos.AgentCreateDto;
import com.sistema.ventas.app.application.agents.services.AgentService;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final AgentService _service;

    public  AgentController(AgentService service) {
        this._service = service;
    }

    @PostMapping
    public ResponseAPI CreateClient(@RequestBody AgentCreateDto dto) {
        return _service.createClient(dto);
    }

}
