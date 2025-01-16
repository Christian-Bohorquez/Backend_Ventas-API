package com.sistema.ventas.app.infrastructure.data.mappers;

import com.sistema.ventas.app.domain.bookings.builders.BookingBuilder;
import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.routes.builders.RouteBuilder;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.infrastructure.data.entities.BookingEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingMapperInfra {

    public BookingEntity toCreateEntity(Booking bookingDomain) {
        BookingEntity entity = new BookingEntity();
        entity.setId(bookingDomain.getId());
        entity.setBookingDate(bookingDomain.getBookingDate());
        entity.setBookingTime(bookingDomain.getBookingTime());
        entity.setNumberOfPeople(bookingDomain.getNumberOfPeople());
        entity.setTotalPrice(bookingDomain.getTotalPrice());
        entity.setReferenceNumber(bookingDomain.getReferenceNumber());
        entity.setStatus(bookingDomain.getStatus().getValue());
        return entity;
    }

    public BookingEntity toUpdateEntity(BookingEntity entity, Booking domain) {
        entity.setBookingDate(domain.getBookingDate());
        entity.setBookingTime(domain.getBookingTime());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

    public Booking toDomain(BookingEntity entity) {
        Route route = new RouteBuilder()
            .withId(entity.getRoute().getId())
            .withStartLocation(entity.getRoute().getStartLocation())
            .withEndLocation(entity.getRoute().getEndLocation())
            .withPrice(entity.getRoute().getPrice())
            .build();

        return new BookingBuilder()
            .withId(entity.getId())
            .withClientId(entity.getClient().getId())
            .withRouteId(route)
            .withBookingDate(entity.getBookingDate())
            .withBookingTime(entity.getBookingTime())
            .withNumberOfPeople(entity.getNumberOfPeople())
            .withTotalPrice(entity.getTotalPrice())
            .withReferenceNumber(entity.getReferenceNumber())
            .withStatus(BookingStatus.fromValue(entity.getStatus()))
            .build();
    }
}
