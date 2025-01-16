package com.sistema.ventas.app.application.bookings.dtos;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class BookingCreateDto {
    private UUID clientId;
    private UUID routeId;
    private Date bookingDate;
    private LocalTime bookingTime;
    private int numberOfPeople;

    public UUID getClientId() { return clientId; }
    public UUID getRouteId() { return routeId; }
    public Date getBookingDate() { return bookingDate; }
    public LocalTime getBookingTime() { return bookingTime; }
    public int getNumberOfPeople() { return numberOfPeople; }
}
