package com.sistema.ventas.app.infrastructure.data.mappers;

import com.sistema.ventas.app.domain.billings.builders.BillingBuilder;
import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.bookings.builders.BookingBuilder;
import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.routes.builders.RouteBuilder;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.infrastructure.data.entities.BillingEntity;
import org.springframework.stereotype.Component;

@Component
public class BillingMapperInfra {

    public BillingEntity toCreateEntity(Billing domain) {
        BillingEntity entity = new BillingEntity();
        entity.setId(domain.getId());
        entity.setCustomerIdentification(domain.getIdentification().getValue());
        entity.setCustomerName(domain.getCustomerName());
        entity.setCustomerEmail(domain.getCustomerEmail().getValue());
        entity.setBillingDate(domain.getBillingDate());
        entity.setTotalPrice(domain.getTotalPrice());
        return entity;
    }

    public Billing toDomain(BillingEntity entity) {

        Route route = new RouteBuilder()
            .withId(entity.getBooking().getRoute().getId())
            .withStartLocation(entity.getBooking().getRoute().getStartLocation())
            .withEndLocation(entity.getBooking().getRoute().getEndLocation())
            .withPrice(entity.getBooking().getRoute().getPrice())
            .build();

        Booking booking = new BookingBuilder()
            .withId(entity.getBooking().getId())
            .withClientId(entity.getBooking().getClient().getId())
            .withRouteId(route)
            .withBookingDate(entity.getBooking().getBookingDate())
            .withBookingTime(entity.getBooking().getBookingTime())
            .withNumberOfPeople(entity.getBooking().getNumberOfPeople())
            .withTotalPrice(entity.getTotalPrice())
            .withReferenceNumber(entity.getBooking().getReferenceNumber())
            .withStatus(BookingStatus.fromValue(entity.getBooking().getStatus()))
            .build();

        return new BillingBuilder()
            .withId(entity.getId())
            .withIdentification(entity.getCustomerIdentification())
            .withCustomerName(entity.getCustomerName())
            .withCustomerEmail(entity.getCustomerEmail())
            .withBooking(booking)
            .withBillingDate(entity.getBillingDate())
            .withPaymentId(entity.getPayment().getId())
            .withTotalPrice(entity.getTotalPrice())
            .build();

    }

}
