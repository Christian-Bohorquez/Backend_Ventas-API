package com.sistema.ventas.app.domain.payments.ports.out;

import com.sistema.ventas.app.domain.payments.models.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPaymentRepositoryPort {

    void save(Payment Payment);

    Optional<Payment> getById(UUID id);

    Optional<Payment> getByName(String name);

    List<Payment> getAll();

}
