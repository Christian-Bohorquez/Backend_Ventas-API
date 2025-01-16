package com.sistema.ventas.app.domain.bookings.builders;

import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.routes.models.Route;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class BookingBuilder {
    private UUID _id;
    private UUID _clientId;
    private Route _routeId;
    private Date _bookingDate;
    private LocalTime _bookingTime;
    private int _numberOfPeople;
    private double _totalPrice;
    private String _referenceNumber;
    private BookingStatus _status;

    public BookingBuilder() { }

    public BookingBuilder withId(UUID id) {
        this._id = id;
        return this;
    }

    public BookingBuilder withClientId(UUID clientId) {
        this._clientId = clientId;
        return this;
    }

    public BookingBuilder withRouteId(Route routeId) {
        this._routeId = routeId;
        return this;
    }

    public BookingBuilder withBookingDate(Date bookingDate) {
        this._bookingDate = bookingDate;
        return this;
    }

    public BookingBuilder withBookingTime(LocalTime bookingTime) {
        this._bookingTime = bookingTime;
        return this;
    }

    public BookingBuilder withNumberOfPeople(int numberOfPeople) {
        this._numberOfPeople = numberOfPeople;
        return this;
    }

    public BookingBuilder withTotalPrice(double totalPrice) {
        this._totalPrice = totalPrice;
        return this;
    }

    public BookingBuilder withReferenceNumber(String referenceNumber) {
        this._referenceNumber = referenceNumber;
        return this;
    }

    public BookingBuilder withStatus(BookingStatus status) {
        this._status = status;
        return this;
    }

    public Booking build() {
        return Booking.create(
            _id,
            _clientId,
            _routeId,
            _bookingDate,
            _bookingTime,
            _numberOfPeople,
            _totalPrice,
            _referenceNumber,
            _status
        );
    }
}
