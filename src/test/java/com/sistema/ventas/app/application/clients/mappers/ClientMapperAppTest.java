package com.sistema.ventas.app.application.clients.mappers;
import com.sistema.ventas.app.application.clients.dtos.ClientCreateDto;
import com.sistema.ventas.app.application.clients.dtos.ClientGetDto;
import com.sistema.ventas.app.application.clients.dtos.ClientUpdateDto;
import com.sistema.ventas.app.domain.clients.builders.ClientBuilder;
import com.sistema.ventas.app.domain.clients.models.Client;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientMapperAppTest {

    @Test
    void toClient_ValidDto_CreatesClient() {
        // Crear un ClientCreateDto válido
        ClientCreateDto dto = mock(ClientCreateDto.class);
        when(dto.getIdentification()).thenReturn("0942673971");
        when(dto.getFirstName()).thenReturn("Juan");
        when(dto.getLastName()).thenReturn("Pérez");
        when(dto.getEmail()).thenReturn("juan@gmail.com");


        // Crear el mapper
        ClientMapperApp mapper = new ClientMapperApp();

        // Convertir el DTO en un objeto Client
        Client client = mapper.toClient(dto);

        // Verificar que el cliente se ha creado correctamente
        assertNotNull(client);
        assertEquals(dto.getIdentification(), client.getIdentification().getValue());
        assertEquals(dto.getFirstName(), client.getFirstName().getValue());
        assertEquals(dto.getLastName(), client.getLastName().getValue());
        assertEquals(dto.getEmail(), client.getEmail().getValue());

        System.out.println("Prueba exitosa: El mapeo de ClientCreateDto a Client fue exitoso.");
    }


    @Test
    void toClient_ValidUpdateDto_UpdatesExistingClient() {
        // Crear un mock de ClientUpdateDto válido
        ClientUpdateDto dto = mock(ClientUpdateDto.class);
        when(dto.getId()).thenReturn(UUID.randomUUID()); // ID del cliente existente (simulado)
        when(dto.getFirstName()).thenReturn("Juan");
        when(dto.getLastName()).thenReturn("Pérez");
        when(dto.getEmail()).thenReturn("juan_update@gmail.com");

        // Crear un cliente existente
        UUID clientId = UUID.randomUUID();
        Client existingClient = new ClientBuilder().withId(clientId)
                .withIdentification("0942673971")
                .withFirstName("Juan")
                .withLastName("Pérez")
                .withEmail("juan@gmail.com")
                .build();

        // Crear el mapper
        ClientMapperApp mapper = new ClientMapperApp();

        // Usamos el método toClient con el DTO y el cliente existente
        Client updatedClient = mapper.toClient(dto, existingClient);

        // Verificar que las propiedades del cliente existente se han actualizado correctamente
        assertNotNull(updatedClient);
        assertEquals(dto.getFirstName(), updatedClient.getFirstName().getValue());
        assertEquals(dto.getLastName(), updatedClient.getLastName().getValue());
        assertEquals(dto.getEmail(), updatedClient.getEmail().getValue());

        System.out.println("Prueba exitosa: El mapeo de ClientUpdateDto a Client fue exitoso.");
    }




    // Test para toDtoGet(Client client)
    @Test
    void toDtoGet_ValidClient_CreatesClientGetDto() {
        UUID clientId = UUID.randomUUID();
        Client client = new ClientBuilder().withId(clientId)
                .withIdentification("0942673971")
                .withFirstName("Juan")
                .withLastName("Pérez")
                .withEmail("juan@gmail.com")
                .build();

        ClientMapperApp mapper = new ClientMapperApp();
        ClientGetDto dto = mapper.toDtoGet(client);

        assertNotNull(dto);
        assertEquals(client.getId(), dto.getId());
        assertEquals(client.getIdentification().getValue(), dto.getIdentification());
        assertEquals(client.getFirstName().getValue(), dto.getFirstName());
        assertEquals(client.getLastName().getValue(), dto.getLastName());
        assertEquals(client.getEmail().getValue(), dto.getEmail());
        System.out.println("Prueba exitosa: El mapeo de Client a ClientGetDto fue exitoso.");
    }
}