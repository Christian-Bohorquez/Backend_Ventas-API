package com.sistema.ventas.app.application.bookings.mappers;

import com.sistema.ventas.app.application.bookings.dtos.BookingCreateDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingGetDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingUpdateDto;
import com.sistema.ventas.app.domain.bookings.builders.BookingBuilder;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.routes.models.Route;
import org.springframework.stereotype.Component;

@Component
public class BookingMapperApp {

    public Booking toBooking(BookingCreateDto dto, Route route) {
        return new BookingBuilder()
            .withClientId(dto.getClientId())
            .withRouteId(route)
            .withBookingDate(dto.getBookingDate())
            .withBookingTime(dto.getBookingTime())
            .withNumberOfPeople(dto.getNumberOfPeople())
            .build();
    }

    public Booking toBooking(BookingUpdateDto dto, Booking existingBooking) {
        existingBooking.updateBookingDateAndTime(dto.getBookingDate(), dto.getBookingTime());
        return existingBooking;
    }

    public BookingGetDto toDtoGet(Booking booking, Client client) {
        return new BookingGetDto(
            booking.getId(),
            booking.getClientId(),
            client.getFirstName().getValue() + " " + client.getLastName().getValue(),
            booking.getRoute().getId(),
            booking.getRoute().getStartLocation() + " - " + booking.getRoute().getEndLocation(),
            booking.getBookingDate(),
            booking.getBookingTime(),
            booking.getNumberOfPeople(),
            booking.getTotalPrice(),
            booking.getReferenceNumber(),
            booking.getStatus().getValue()
        );
    }
}
