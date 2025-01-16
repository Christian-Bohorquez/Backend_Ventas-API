package com.sistema.ventas.app.application.billings.mappers;

import com.sistema.ventas.app.application.billings.dtos.BillingCreateDto;
import com.sistema.ventas.app.application.billings.dtos.BillingGetDto;
import com.sistema.ventas.app.domain.billings.builders.BillingBuilder;
import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.payments.models.Payment;
import org.springframework.stereotype.Component;

@Component
public class BillingMapperApp {

    public Billing toBilling(BillingCreateDto dto, Booking booking) {
        return new BillingBuilder()
            .withIdentification(dto.getCustomerIdentification())
            .withCustomerName(dto.getCustomerName())
            .withCustomerEmail(dto.getCustomerEmail())
            .withPaymentId((dto.getPaymentId()))
            .withBooking(booking)
            .build();
    }

    public BillingGetDto toDtoGet(Billing billing, Payment payment) {
        return new BillingGetDto(
            billing.getId(),
            billing.getIdentification().getValue(),
            billing.getCustomerName(),
            billing.getCustomerEmail().getValue(),
            billing.getBooking().getId(),
            payment.getName(),
            billing.getTotalPrice()
        );
    }

}
