package com.sistema.ventas.app.application.clients.mappers;

import com.sistema.ventas.app.application.clients.dtos.ClientCreateDto;
import com.sistema.ventas.app.application.clients.dtos.ClientGetDto;
import com.sistema.ventas.app.application.clients.dtos.ClientUpdateDto;
import com.sistema.ventas.app.domain.clients.builders.ClientBuilder;
import com.sistema.ventas.app.domain.clients.models.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperApp {

    public Client toClient(ClientCreateDto dto) {
        return new ClientBuilder()
            .withIdentification(dto.getIdentification())
            .withFirstName(dto.getFirstName())
            .withLastName(dto.getLastName())
            .withEmail(dto.getEmail())
            .build();
    }

    public Client toClient(ClientUpdateDto dto, Client existingClient) {
        existingClient.UpdateFirstName(dto.getFirstName());
        existingClient.UpdateLastName(dto.getLastName());
        existingClient.UpdateEmail(dto.getEmail());
        return existingClient;
    }

    public ClientGetDto toDtoGet(Client client) {
        return new ClientGetDto(
            client.getId(),
            client.getIdentification().getValue(),
            client.getFirstName().getValue(),
            client.getLastName().getValue(),
            client.getEmail().getValue());
    }

}
