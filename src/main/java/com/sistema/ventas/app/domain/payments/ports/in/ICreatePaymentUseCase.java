package com.sistema.ventas.app.domain.payments.ports.in;

import com.sistema.ventas.app.domain.payments.models.Payment;

public interface ICreatePaymentUseCase {
    void execute(Payment payment);
}
