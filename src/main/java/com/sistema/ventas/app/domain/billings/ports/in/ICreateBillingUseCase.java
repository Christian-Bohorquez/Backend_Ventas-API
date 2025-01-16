package com.sistema.ventas.app.domain.billings.ports.in;

import com.sistema.ventas.app.domain.billings.models.Billing;

public interface ICreateBillingUseCase {
    void execute(Billing billing);
}
