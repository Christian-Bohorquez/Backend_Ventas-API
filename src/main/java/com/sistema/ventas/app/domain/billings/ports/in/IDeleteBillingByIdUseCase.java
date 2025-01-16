package com.sistema.ventas.app.domain.billings.ports.in;

import java.util.UUID;

public interface IDeleteBillingByIdUseCase {
    void execute(UUID id);
}
