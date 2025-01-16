package com.sistema.ventas.app.application.payments.validators;

import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentValidator {

    private final IPaymentRepositoryPort _repository;

    public PaymentValidator(
            IPaymentRepositoryPort repository)
    {
        this._repository = repository;
    }

    public void validateCreate(Payment payment) {
        _repository.getByName(payment.getName())
            .ifPresent((existingName -> {
                throw new UniqueConstraintViolationException("El nombre");
            }));
    }

    public void validateGetById(UUID id) {
        _repository.getById(id)
            .orElseThrow(() -> new NotFoundException("Forma de pago"));
    }

}
