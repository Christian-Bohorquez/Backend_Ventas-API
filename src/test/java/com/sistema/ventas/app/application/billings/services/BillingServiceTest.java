package com.sistema.ventas.app.application.billings.services;

import com.sistema.ventas.app.application.billings.dtos.BillingCreateDto;
import com.sistema.ventas.app.application.billings.dtos.BillingGetDto;
import com.sistema.ventas.app.application.billings.mappers.BillingMapperApp;
import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.in.ICreateBillingUseCase;
import com.sistema.ventas.app.domain.billings.ports.in.IDeleteBillingByIdUseCase;
import com.sistema.ventas.app.domain.billings.ports.in.IGetAllBillingsUseCase;
import com.sistema.ventas.app.domain.billings.ports.in.IGetBillingByBookingIdUseCase;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.in.IGetBookingByIdUseCase;
import com.sistema.ventas.app.domain.payments.ports.in.IGetPaymentByIdUseCase;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class BillingServiceTest {

    private BillingMapperApp mapper;
    private ICreateBillingUseCase createBillingUseCase;
    private IDeleteBillingByIdUseCase deleteBillingByIdUseCase;
    private IGetAllBillingsUseCase getAllBillingsUseCase;
    private IGetBillingByBookingIdUseCase getBillingByBookingIdUseCase;
    private IGetBookingByIdUseCase getBookingByIdUseCase;
    private IGetPaymentByIdUseCase getPaymentByIdUseCase;
    private BillingService service;

    @BeforeEach
    void setup() {
        mapper = mock(BillingMapperApp.class);
        createBillingUseCase = mock(ICreateBillingUseCase.class);
        deleteBillingByIdUseCase = mock(IDeleteBillingByIdUseCase.class);
        getAllBillingsUseCase = mock(IGetAllBillingsUseCase.class);
        getBillingByBookingIdUseCase = mock(IGetBillingByBookingIdUseCase.class);
        getBookingByIdUseCase = mock(IGetBookingByIdUseCase.class);
        getPaymentByIdUseCase = mock(IGetPaymentByIdUseCase.class);
        service = new BillingService(
                mapper,
                createBillingUseCase,
                deleteBillingByIdUseCase,
                getAllBillingsUseCase,
                getBillingByBookingIdUseCase,
                getBookingByIdUseCase,
                getPaymentByIdUseCase
        );
    }

    @Test
    void createBilling_ValidDto_ReturnsSuccessResponse() {
        BillingCreateDto dto = mock(BillingCreateDto.class);
        Booking booking = mock(Booking.class);
        Billing billing = mock(Billing.class);

        // GIVEN
        when(dto.getBookingId()).thenReturn(UUID.randomUUID());
        when(getBookingByIdUseCase.execute(dto.getBookingId())).thenReturn(booking);
        when(mapper.toBilling(dto, booking)).thenReturn(billing);

        // WHEN
        ResponseAPI<String> response = service.createBilling(dto);

        // THEN
        assertEquals(201, response.getStatusCode());  // Cambié aquí
        assertEquals("Factura generada con éxito", response.getMessage());
        verify(createBillingUseCase).execute(billing);

        // Prueba exitosa: La factura se generó correctamente y se devolvió la respuesta esperada.
        System.out.println("Prueba exitosa: La factura se generó correctamente con el código 201.");
    }

    @Test
    void getAllBillings_NoBillings_ReturnsEmptyResponse() {
        // GIVEN
        when(getAllBillingsUseCase.execute()).thenReturn(List.of());

        // WHEN
        ResponseAPI<List<BillingGetDto>> response = service.getAllBillings();

        // THEN
        assertEquals(200, response.getStatusCode());  // Cambié aquí
        assertEquals("No se encontraron facturas registradas", response.getMessage());

        // Prueba exitosa: No se encontraron facturas y se devolvió la respuesta vacía esperada.
        System.out.println("Prueba exitosa: No se encontraron facturas registradas.");
    }
}
