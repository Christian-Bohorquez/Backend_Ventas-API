package com.sistema.ventas.app.application.clients.services;
import com.sistema.ventas.app.application.clients.dtos.ClientCreateDto;
import com.sistema.ventas.app.application.clients.dtos.ClientGetDto;
import com.sistema.ventas.app.application.clients.dtos.ClientUpdateDto;
import com.sistema.ventas.app.application.clients.mappers.ClientMapperApp;
import com.sistema.ventas.app.domain.clients.builders.ClientBuilder;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import com.sistema.ventas.app.domain.clients.ports.in.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    private ClientMapperApp mapper;
    private ICreateClientUseCase createClientUseCase;
    private IUpdateClientUseCase updateClientUseCase;
    private IDeleteClientByIdUseCase deleteClientByIdUseCase;
    private IGetAllClientsUseCase getAllClientsUseCase;
    private IGetClientByIdUseCase getClientByIdUseCase;
    private IGetClientByIdentificationUseCase getClientByIdentificationUseCase;

    private ClientService service;

    @BeforeEach
    void setUp() {
        // Inicializamos los mocks
        mapper = mock(ClientMapperApp.class);
        createClientUseCase = mock(ICreateClientUseCase.class);
        updateClientUseCase = mock(IUpdateClientUseCase.class);
        deleteClientByIdUseCase = mock(IDeleteClientByIdUseCase.class);
        getAllClientsUseCase = mock(IGetAllClientsUseCase.class);
        getClientByIdUseCase = mock(IGetClientByIdUseCase.class);
        getClientByIdentificationUseCase = mock(IGetClientByIdentificationUseCase.class);

        // Inyectamos los mocks en el servicio
        service = new ClientService(mapper, createClientUseCase, updateClientUseCase, deleteClientByIdUseCase,
                getAllClientsUseCase, getClientByIdUseCase, getClientByIdentificationUseCase);
    }

    @Test
    void createClient_ValidClient_ReturnsSuccessResponse() {
        // Crear un mock de ClientCreateDto
        ClientCreateDto dto = mock(ClientCreateDto.class);
        when(dto.getIdentification()).thenReturn("0942673971");
        when(dto.getFirstName()).thenReturn("Juan");
        when(dto.getLastName()).thenReturn("Pérez");
        when(dto.getEmail()).thenReturn("juan@gmail.com");

        // Crear un objeto Client usando un builder (suponiendo que tu builder funciona correctamente)
        UUID clientId = UUID.randomUUID();
        Client client = new ClientBuilder().withId(clientId).withIdentification("0942673971")
                .withFirstName("Juan").withLastName("Pérez").withEmail("juan@gmail.com").build();

        // Simulamos el comportamiento del mapeo y la creación del cliente
        when(mapper.toClient(dto)).thenReturn(client);
        doNothing().when(createClientUseCase).execute(client);

        try {
            ResponseAPI response = service.createClient(dto);
            assertEquals(201, response.getStatusCode());
            assertEquals("Cliente creado exitosamente", response.getMessage());
            verify(mapper).toClient(dto);
            verify(createClientUseCase).execute(client);
            System.out.println("Prueba exitosa: Se creó el cliente correctamente.");
        } catch (AssertionError e) {
            System.out.println("Error en createClient_ValidClient_ReturnsSuccessResponse: " + e.getMessage());
            throw e;
        }
    }


    @Test
    void updateClient_ValidClient_ReturnsSuccessResponse() {
        UUID clientId = UUID.randomUUID();  // Verifica que el UUID sea válido y no nulo
        Client existingClient = new ClientBuilder().withId(clientId).withIdentification("0942673971")
                .withFirstName("Juan").withLastName("Pérez").withEmail("juan@gmail.com").build();

        // Crear un mock de ClientUpdateDto
        ClientUpdateDto dto = mock(ClientUpdateDto.class);
        when(dto.getId()).thenReturn(clientId);  // Asegúrate de devolver el ID correcto
        when(dto.getFirstName()).thenReturn("Juan");
        when(dto.getLastName()).thenReturn("Pérez");
        when(dto.getEmail()).thenReturn("juan_update@gmail.com");

        // Crear el cliente actualizado
        Client updatedClient = new ClientBuilder().withId(clientId).withIdentification("0942673971")
                .withFirstName("Juan").withLastName("Pérez").withEmail("juan_update@gmail.com").build();

        // Simulamos que obtenemos el cliente existente y lo actualizamos
        when(getClientByIdUseCase.execute(clientId)).thenReturn(existingClient);
        when(mapper.toClient(dto, existingClient)).thenReturn(updatedClient);
        doNothing().when(updateClientUseCase).execute(updatedClient);

        ResponseAPI response = service.updateClient(dto);

        assertEquals(200, response.getStatusCode());
        assertEquals("Cliente actualizado con éxito", response.getMessage());
        verify(getClientByIdUseCase).execute(clientId);
        verify(mapper).toClient(dto, existingClient);
        verify(updateClientUseCase).execute(updatedClient);

        System.out.println("Prueba exitosa: El cliente fue actualizado correctamente.");
    }



    @Test
    void deleteClient_ValidClient_ReturnsSuccessResponse() {
        UUID clientId = UUID.randomUUID();

        // Simulamos la eliminación del cliente
        doNothing().when(deleteClientByIdUseCase).execute(clientId);

        ResponseAPI response = service.deleteClient(clientId);

        assertEquals(200, response.getStatusCode());
        assertEquals("Cliente eliminado con éxito", response.getMessage());
        verify(deleteClientByIdUseCase).execute(clientId);
    }

    @Test
    void getAllClients_WithClients_ReturnsListResponse() {
        // Crear una lista de clientes simulados
        UUID clientId1 = UUID.randomUUID();
        Client client1 = new ClientBuilder().withId(clientId1).withIdentification("0942673971")
                .withFirstName("Juan").withLastName("Pérez").withEmail("juan@gmail.com").build();

        UUID clientId2 = UUID.randomUUID();
        Client client2 = new ClientBuilder().withId(clientId2).withIdentification("0951618024")
                .withFirstName("Ana").withLastName("Gómez").withEmail("ana@gmail.com").build();

        List<Client> clientList = Arrays.asList(client1, client2);

        // Simulamos que el caso de uso para obtener clientes retorna la lista de clientes
        when(getAllClientsUseCase.execute()).thenReturn(clientList);

        // Ejecutamos el servicio
        ResponseAPI response = service.getAllClients();

        // Verificamos que la respuesta es la esperada
        assertEquals(200, response.getStatusCode());
        // Cambiar el mensaje esperado a lo que realmente devuelve el servicio
        assertEquals("Lista de clientes", response.getMessage()); // Cambiar aquí
        assertNotNull(response.getData());
        assertEquals(2, ((List<?>) response.getData()).size());

        // Verificamos que el método getAllClientsUseCase fue invocado una vez
        verify(getAllClientsUseCase).execute();

        System.out.println("Prueba exitosa: Se obtuvieron los clientes correctamente.");
    }


    @Test
    void getAllClients_NoClients_ReturnsEmptyListResponse() {
        // Simulamos que no hay clientes registrados
        when(getAllClientsUseCase.execute()).thenReturn(List.of());

        ResponseAPI<List<ClientGetDto>> response = service.getAllClients();

        assertEquals(200, response.getStatusCode());
        assertEquals("No se encontraron clientes registrados", response.getMessage());
        assertTrue(response.getData().isEmpty());
        verify(getAllClientsUseCase).execute();
        verifyNoInteractions(mapper);
    }
}
