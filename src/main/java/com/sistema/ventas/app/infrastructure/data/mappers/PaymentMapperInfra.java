package com.sistema.ventas.app.infrastructure.data.mappers;

import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.infrastructure.data.entities.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperInfra {

    public PaymentEntity toCreateEntity(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(payment.getId());
        entity.setName(payment.getName());
        return  entity;
    }

    public Payment toDomain(PaymentEntity entity) {
        return new Payment(entity.getId(), entity.getName());
    }


}
