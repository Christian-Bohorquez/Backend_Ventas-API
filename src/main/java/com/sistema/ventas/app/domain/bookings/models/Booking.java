package com.sistema.ventas.app.domain.bookings.models;

import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.utils.BookingReferenceGenerator;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.shared.exceptions.BusinessRuleViolationException;
import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class Booking {
    private UUID id;
    private UUID clientId;
    private Route route;
    private Date bookingDate;
    private LocalTime bookingTime;
    private int numberOfPeople;
    private double totalPrice;
    private String referenceNumber;
    private BookingStatus status;

    private Booking(
        UUID id, UUID clientId, Route route, Date bookingDate, LocalTime bookingTime,
        int numberOfPeople, double totalPrice, String referenceNumber, BookingStatus status )
    {
        this.id = id;
        this.clientId = clientId;
        this.route = route;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        this.referenceNumber = referenceNumber;
        this.status = status;

        this.validate();
    }

    public static Booking create(
            UUID id, UUID clientId, Route route, Date bookingDate, LocalTime bookingTime,
            int numberOfPeople, double totalPrice, String referenceNumber, BookingStatus status ) {
        if (id == null)  {
            status = BookingStatus.RESERVADA;
            totalPrice = route.getPrice().getValue() * numberOfPeople;
            referenceNumber = BookingReferenceGenerator.generate();
        }

        return new Booking(
            id != null ? id : UUID.randomUUID(),
            clientId,
            route,
            bookingDate,
            bookingTime,
            numberOfPeople,
            totalPrice,
            referenceNumber,
            status
        );
    }

    public void updateBookingDateAndTime(Date newBookingDate, LocalTime newBookingTime) {
        if (newBookingDate == null || newBookingDate.before(new Date())) throw new BusinessRuleViolationException("La nueva fecha de reserva no puede ser anterior a la fecha actual.");
        if (newBookingTime == null) throw new RequiredFieldMissingException("La nueva hora de reserva no puede ser nula.");
        this.bookingDate = newBookingDate;
        this.bookingTime = newBookingTime;
    }

    private void validate() {
        if (clientId == null) throw new RequiredFieldMissingException("Cliente");
        if (route == null) throw new RequiredFieldMissingException("Ruta");
        if (bookingDate == null) throw new RequiredFieldMissingException("Fecha de reserva");
        if (bookingTime == null) throw new RequiredFieldMissingException("Hora de reserva");
        if (status == null) throw new RequiredFieldMissingException("Estado de reserva");
        if (numberOfPeople <= 0)
            throw new BusinessRuleViolationException("La cantidad de personas debe ser mayor a cero.");
        if (totalPrice <= 0) throw new BusinessRuleViolationException("El precio total debe ser mayor a cero.");
    }

    public UUID getId() { return id; }
    public UUID getClientId() { return clientId; }
    public Route getRoute() { return route; }
    public Date getBookingDate() { return bookingDate; }
    public LocalTime getBookingTime() { return bookingTime; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public BookingStatus getStatus() {  return status; }
    public int getNumberOfPeople() { return numberOfPeople; }
    public String getReferenceNumber() { return referenceNumber; }
    public void setStatus(BookingStatus status) { this.status = status; }

}
