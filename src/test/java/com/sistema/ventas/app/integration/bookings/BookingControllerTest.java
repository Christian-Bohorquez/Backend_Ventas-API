package com.sistema.ventas.app.integration.bookings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.ventas.app.application.bookings.dtos.BookingCreateDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingGetDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingUpdateDto;
import com.sistema.ventas.app.application.bookings.services.BookingService;
import com.sistema.ventas.app.infrastructure.security.JwtTokenProvider;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) // ✅ Activa Mockito en JUnit 5
@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc; // ✅ Inyectado desde la configuración

    @Autowired
    private ObjectMapper objectMapper; // ✅ Inyectado desde la configuración

    @Mock
    private BookingService bookingService; // ✅ Mock de BookingService

    @Mock
    private JwtTokenProvider jwtTokenProvider; // ✅ Mock para validación de JWT

    @InjectMocks
    private BookingControllerTest bookingControllerTest; // ✅ Inyección de mocks

    private BookingCreateDto bookingCreateDto;
    private BookingUpdateDto bookingUpdateDto;
    private BookingGetDto bookingGetDto;
    private UUID bookingId;
    private String mockJwtToken;

    @BeforeEach
    void setUp() {
        bookingId = UUID.randomUUID();
        mockJwtToken = "Bearer mock-token"; // ✅ Simulación de token JWT

        bookingCreateDto = new BookingCreateDto();
        bookingUpdateDto = new BookingUpdateDto();
        bookingGetDto = new BookingGetDto(
                bookingId, UUID.randomUUID(), "Juan Perez",
                UUID.randomUUID(), "New York - Boston",
                new Date(), LocalTime.now(), 2, 100.0,
                "ABC123", "RESERVADA"
        );

        // ✅ Simulación de validación de token
        when(jwtTokenProvider.validateToken("mock-token")).thenReturn(true);
        when(jwtTokenProvider.getClaimsFromToken("mock-token")).thenReturn(null); // No necesitamos claims en pruebas

        // ✅ Configuración de respuestas simuladas
        when(bookingService.createBooking(any(BookingCreateDto.class)))
                .thenReturn(new ResponseAPI<>(201, "Reserva creada exitosamente", "ABC123"));

        when(bookingService.updateBooking(any(BookingUpdateDto.class)))
                .thenReturn(new ResponseAPI<>(200, "Reserva actualizada con éxito"));

        when(bookingService.getAllBookings())
                .thenReturn(new ResponseAPI<>(200, "Lista de reservas", Collections.singletonList(bookingGetDto)));

        when(bookingService.getBookingById(bookingId))
                .thenReturn(new ResponseAPI<>(200, "Información de la reserva", bookingGetDto));

        when(bookingService.deleteBooking(bookingId))
                .thenReturn(new ResponseAPI<>(200, "Reserva cancelada con éxito"));
    }

    @Test
    @DisplayName("✅ Debe crear una reserva correctamente")
    void shouldCreateBooking() throws Exception {
        mockMvc.perform(post("/api/booking")
                        .header(HttpHeaders.AUTHORIZATION, mockJwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Reserva creada exitosamente")))
                .andDo(result -> System.out.println("✔ Test de creación de reserva exitoso"));
    }

    @Test
    @DisplayName("✅ Debe actualizar una reserva correctamente")
    void shouldUpdateBooking() throws Exception {
        mockMvc.perform(put("/api/booking")
                        .header(HttpHeaders.AUTHORIZATION, mockJwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Reserva actualizada con éxito")))
                .andDo(result -> System.out.println("✔ Test de actualización de reserva exitoso"));
    }

    @Test
    @DisplayName("✅ Debe obtener todas las reservas correctamente")
    void shouldGetAllBookings() throws Exception {
        mockMvc.perform(get("/api/booking")
                        .header(HttpHeaders.AUTHORIZATION, mockJwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Lista de reservas")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andDo(result -> System.out.println("✔ Test de obtención de reservas exitoso"));
    }

    @Test
    @DisplayName("✅ Debe obtener una reserva por ID correctamente")
    void shouldGetBookingById() throws Exception {
        mockMvc.perform(get("/api/booking/{id}", bookingId)
                        .header(HttpHeaders.AUTHORIZATION, mockJwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Información de la reserva")))
                .andExpect(jsonPath("$.data.referenceNumber", is("ABC123")))
                .andDo(result -> System.out.println("✔ Test de obtención de reserva por ID exitoso"));
    }

    @Test
    @DisplayName("✅ Debe eliminar una reserva correctamente")
    void shouldDeleteBooking() throws Exception {
        mockMvc.perform(delete("/api/booking/{id}", bookingId)
                        .header(HttpHeaders.AUTHORIZATION, mockJwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Reserva cancelada con éxito")))
                .andDo(result -> System.out.println("✔ Test de eliminación de reserva exitoso"));
    }

    /**
     * 📌 Clase de configuración para inyectar `MockMvc` y `ObjectMapper`
     */
    @Configuration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }
}
