package com.sistema.ventas.app.application.billings.validators;

import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BillingValidator {
    private final IBillingRepositoryPort _billingRepository;
    private final IBookingRepositoryPort _bookingRepository;
    private final IPaymentRepositoryPort _paymentRepository;

    public BillingValidator(
        IBillingRepositoryPort billingRepository,
        IBookingRepositoryPort bookingRepository,
        IPaymentRepositoryPort paymentRepository )
    {
        this._billingRepository = billingRepository;
        this._bookingRepository = bookingRepository;
        this._paymentRepository = paymentRepository;
    }

    public void validateCreate(Billing billing) {
        _bookingRepository.getById(billing.getBooking().getId())
            .orElseThrow(() -> new NotFoundException("Reserva"));

        _paymentRepository.getById(billing.getPaymentId())
            .orElseThrow(() -> new NotFoundException("Forma de pago"));
    }

    public void validateGetById(UUID id) {
        _billingRepository.getById(id)
            .orElseThrow(() -> new NotFoundException("Factura"));
    }
}
