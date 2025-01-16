package com.sistema.ventas.app.domain.billings.ports.out;

import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.shared.repositories.IRepository;

import java.util.Optional;
import java.util.UUID;

public interface IBillingRepositoryPort extends IRepository<Billing> {
    Optional<Billing> getByBookingId(UUID id);
}
