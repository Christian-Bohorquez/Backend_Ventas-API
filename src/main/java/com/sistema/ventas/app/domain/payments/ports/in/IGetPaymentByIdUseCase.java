package com.sistema.ventas.app.domain.payments.ports.in;

import com.sistema.ventas.app.domain.payments.models.Payment;

import java.util.UUID;

public interface IGetPaymentByIdUseCase {
    Payment execute(UUID id);
}
