package com.sistema.ventas.app.domain.billings.ports.in;

import com.sistema.ventas.app.domain.billings.models.Billing;

import java.util.List;

public interface IGetAllBillingsUseCase {
    List<Billing> execute();
}
