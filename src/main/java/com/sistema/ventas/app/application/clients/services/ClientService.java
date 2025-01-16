package com.sistema.ventas.app.application.clients.services;

import com.sistema.ventas.app.application.clients.dtos.ClientCreateDto;
import com.sistema.ventas.app.application.clients.dtos.ClientGetDto;
import com.sistema.ventas.app.application.clients.dtos.ClientUpdateDto;
import com.sistema.ventas.app.application.clients.mappers.ClientMapperApp;
import com.sistema.ventas.app.domain.clients.ports.in.*;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientMapperApp _mapper;
    private final ICreateClientUseCase _createClientUseCase;
    private final IUpdateClientUseCase _updateClientUseCase;
    private final IDeleteClientByIdUseCase _deleteClientByIdUseCase;
    private final IGetAllClientsUseCase _getAllClientsUseCase;
    private final IGetClientByIdUseCase _getClientByIdUseCase;
    private final IGetClientByIdentificationUseCase _getClientByIdentificationUseCase;

    public ClientService(
            ClientMapperApp mapper,
            ICreateClientUseCase createClientUseCase,
            IUpdateClientUseCase updateClientUseCase,
            IDeleteClientByIdUseCase deleteClientByIdUseCase,
            IGetAllClientsUseCase getAllClientsUseCase,
            IGetClientByIdUseCase getClientByIdUseCase,
            IGetClientByIdentificationUseCase getClientByIdentificationUseCase)
    {
        this._mapper = mapper;
        this._createClientUseCase = createClientUseCase;
        this._updateClientUseCase = updateClientUseCase;
        this._deleteClientByIdUseCase = deleteClientByIdUseCase;
        this._getAllClientsUseCase = getAllClientsUseCase;
        this._getClientByIdUseCase = getClientByIdUseCase;
        this._getClientByIdentificationUseCase = getClientByIdentificationUseCase;
    }

    public ResponseAPI createClient(ClientCreateDto dto) {
        var client = _mapper.toClient(dto);
        _createClientUseCase.execute(client);
        return new ResponseAPI(201, "Cliente creado exitosamente");
    }

    public ResponseAPI updateClient(ClientUpdateDto dto) {
        var existingClient = _getClientByIdUseCase.execute(dto.getId());
        var updatedClient = _mapper.toClient(dto, existingClient);
        _updateClientUseCase.execute(updatedClient);
        return new ResponseAPI(200, "Cliente actualizado con éxito");
    }

    public ResponseAPI deleteClient(UUID id) {
        _deleteClientByIdUseCase.execute(id);
        return new ResponseAPI(200, "Cliente eliminado con éxito");
    }

    public ResponseAPI<ClientGetDto> getClientById(UUID clientId) {
        var clientDomain = _getClientByIdUseCase.execute(clientId);
        var clientDTO = _mapper.toDtoGet(clientDomain);
        return new ResponseAPI<>(200, "Información del cliente", clientDTO);
    }

    public ResponseAPI<ClientGetDto> getClientByIdentification(String identification) {
        var clientDomain = _getClientByIdentificationUseCase.execute(identification);
        var clientDTO = _mapper.toDtoGet(clientDomain);
        return new ResponseAPI<>(200, "Información del cliente", clientDTO);
    }

    public ResponseAPI<List<ClientGetDto>>  getAllClients() {
        var clientsDomain = _getAllClientsUseCase.execute();
        var clientsDto = clientsDomain.stream()
                .map(_mapper::toDtoGet)
                .collect(Collectors.toList());

        String message = clientsDto.isEmpty() ?
                "No se encontraron clientes registrados" :
                "Lista de clientes";

        return new ResponseAPI(200, message, clientsDto);
    }

}
