package com.sistema.ventas.app.application.bookings.dtos;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class BookingUpdateDto {
    private UUID id;
    private Date bookingDate;
    private LocalTime bookingTime;

    public UUID getId() { return id; }
    public Date getBookingDate() { return bookingDate; }
    public LocalTime getBookingTime() { return bookingTime; }
}
