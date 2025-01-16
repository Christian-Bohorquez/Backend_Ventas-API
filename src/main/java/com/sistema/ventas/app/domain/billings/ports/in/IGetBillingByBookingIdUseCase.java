package com.sistema.ventas.app.domain.billings.ports.in;

import com.sistema.ventas.app.domain.billings.models.Billing;

import java.util.UUID;

public interface IGetBillingByBookingIdUseCase {
    Billing execute(UUID bookingId);
}
