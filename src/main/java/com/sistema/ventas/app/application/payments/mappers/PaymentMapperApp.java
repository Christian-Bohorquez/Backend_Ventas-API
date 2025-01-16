package com.sistema.ventas.app.application.payments.mappers;

import com.sistema.ventas.app.application.payments.dtos.PaymentCreateDto;
import com.sistema.ventas.app.application.payments.dtos.PaymentGetDto;
import com.sistema.ventas.app.domain.payments.models.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperApp {

    public Payment toPayment(PaymentCreateDto dto) {
        return new Payment(dto.getName());
    }

    public PaymentGetDto toDtoGet(Payment payment) {
        return new PaymentGetDto(payment.getId(), payment.getName());
    }

}
