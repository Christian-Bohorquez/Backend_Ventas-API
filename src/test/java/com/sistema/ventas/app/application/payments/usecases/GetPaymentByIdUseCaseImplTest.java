package com.sistema.ventas.app.application.payments.usecases;

import com.sistema.ventas.app.application.payments.validators.PaymentValidator;
import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GetPaymentByIdUseCaseImplTest {

    private IPaymentRepositoryPort repository;
    private PaymentValidator validator;
    private GetPaymentByIdUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IPaymentRepositoryPort.class);
        validator = Mockito.mock(PaymentValidator.class);
        useCase = new GetPaymentByIdUseCaseImpl(repository, validator);
    }

    @Test
    void execute_IdExists_ReturnsPayment() {
        UUID id = UUID.randomUUID();
        Payment payment = new Payment(id, "Forma de pago válida");

        when(repository.getById(id)).thenReturn(Optional.of(payment));

        try {
            Payment result = useCase.execute(id);
            assertEquals(payment, result);
            verify(validator).validateGetById(id);
            verify(repository).getById(id);
            System.out.println("Prueba exitosa: El ID existe y se obtuvo correctamente el pago.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_IdExists_ReturnsPayment: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_IdDoesNotExist_ThrowsException() {
        UUID id = UUID.randomUUID();

        // Configurar el comportamiento del mock para devolver Optional.empty()
        when(repository.getById(id)).thenReturn(Optional.empty());

        // Usar doAnswer para capturar el comportamiento y lanzar la excepción con el mensaje esperado
        doAnswer(invocation -> {
            throw new RuntimeException("No se encontró el/la Forma de pago.");
        }).when(repository).getById(id);

        // Ejecutar el caso de uso y verificar la excepción
        Exception exception = assertThrows(RuntimeException.class, () -> useCase.execute(id));

        // Verificar que el mensaje de la excepción sea el esperado
        assertEquals("No se encontró el/la Forma de pago.", exception.getMessage());

        // Verificar que los métodos correspondientes fueron llamados
        verify(validator).validateGetById(id);
        verify(repository).getById(id);

        System.out.println("Prueba exitosa: Se detectó correctamente que el ID no existe.");
    }


}