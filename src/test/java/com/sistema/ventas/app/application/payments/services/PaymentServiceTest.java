package com.sistema.ventas.app.application.payments.services;

import com.sistema.ventas.app.application.payments.dtos.PaymentCreateDto;
import com.sistema.ventas.app.application.payments.dtos.PaymentGetDto;
import com.sistema.ventas.app.application.payments.mappers.PaymentMapperApp;
import com.sistema.ventas.app.application.payments.services.PaymentService;
import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.in.ICreatePaymentUseCase;
import com.sistema.ventas.app.domain.payments.ports.in.IGetAllPaymentsUseCase;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    private PaymentMapperApp mapper;
    private ICreatePaymentUseCase createPaymentUseCase;
    private IGetAllPaymentsUseCase getAllPaymentsUseCase;
    private PaymentService service;

    @BeforeEach
    void setUp() {
        mapper = mock(PaymentMapperApp.class);
        createPaymentUseCase = mock(ICreatePaymentUseCase.class);
        getAllPaymentsUseCase = mock(IGetAllPaymentsUseCase.class);
        service = new PaymentService(mapper, createPaymentUseCase, getAllPaymentsUseCase);
    }

    @Test
    void createPayment_ValidPayment_ReturnsSuccessResponse() {
        PaymentCreateDto dto = new PaymentCreateDto();
        Payment payment = new Payment("Nuevo Pago");

        when(mapper.toPayment(dto)).thenReturn(payment);

        try {
            ResponseAPI response = service.createPayment(dto);
            assertEquals(201, response.getStatusCode());
            assertEquals("Forma de pago creada exitosamente", response.getMessage());
            verify(mapper).toPayment(dto);
            verify(createPaymentUseCase).execute(payment);
            System.out.println("Prueba exitosa: Se creó el pago correctamente.");
        } catch (AssertionError e) {
            System.out.println("Error en createPayment_ValidPayment_ReturnsSuccessResponse: " + e.getMessage());
            throw e;
        }
    }


    @Test
    void getAllPayments_WithPayments_ReturnsListResponse() {
        List<Payment> payments = List.of(
                new Payment(UUID.randomUUID(), "Pago 1"),
                new Payment(UUID.randomUUID(), "Pago 2")
        );
        List<PaymentGetDto> paymentsDto = List.of(
                new PaymentGetDto(payments.get(0).getId(), payments.get(0).getName()),
                new PaymentGetDto(payments.get(1).getId(), payments.get(1).getName())
        );

        when(getAllPaymentsUseCase.execute()).thenReturn(payments);
        when(mapper.toDtoGet(payments.get(0))).thenReturn(paymentsDto.get(0));
        when(mapper.toDtoGet(payments.get(1))).thenReturn(paymentsDto.get(1));

        ResponseAPI<List<PaymentGetDto>> response = service.getAllPayments();

        assertEquals(200, response.getStatusCode());
        assertEquals("Lista de formas de pago", response.getMessage());
        assertEquals(paymentsDto, response.getData());
        verify(getAllPaymentsUseCase).execute();
        verify(mapper).toDtoGet(payments.get(0));
        verify(mapper).toDtoGet(payments.get(1));
    }

    @Test
    void getAllPayments_NoPayments_ReturnsEmptyListResponse() {
        when(getAllPaymentsUseCase.execute()).thenReturn(List.of());

        ResponseAPI<List<PaymentGetDto>> response = service.getAllPayments();

        assertEquals(200, response.getStatusCode());
        assertEquals("No se encontraron formas de pago registradas", response.getMessage());
        assertTrue(response.getData().isEmpty());
        verify(getAllPaymentsUseCase).execute();
        verifyNoInteractions(mapper);
    }
}
