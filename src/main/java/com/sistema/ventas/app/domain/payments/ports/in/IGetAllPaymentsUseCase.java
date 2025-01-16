package com.sistema.ventas.app.domain.payments.ports.in;

import com.sistema.ventas.app.domain.payments.models.Payment;

import java.util.List;

public interface IGetAllPaymentsUseCase {
    List<Payment> execute();
}
