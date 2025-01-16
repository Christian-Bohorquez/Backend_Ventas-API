package com.sistema.ventas.app.application.bookings.dtos;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class BookingGetDto {

    private UUID id;
    private UUID clientId;
    private String clientFullName;
    private UUID routeId;
    private String routeDescription;
    private Date bookingDate;
    private LocalTime bookingTime;
    private int numberOfPeople;
    private double totalPrice;
    private String referenceNumber;
    private String status;

    public BookingGetDto(
            UUID id, UUID clientId, String clientFullName, UUID routeId, String routeDescription,
            Date bookingDate, LocalTime bookingTime, int numberOfPeople, double totalPrice, String referenceNumber, String status ) {
        this.id = id;
        this.clientId = clientId;
        this.clientFullName = clientFullName;
        this.routeId = routeId;
        this.routeDescription = routeDescription;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        this.referenceNumber = referenceNumber;
        this.status = status;
    }

    public UUID getId() { return id; }
    public UUID getClientId() { return clientId; }
    public String getClientFullName() { return clientFullName; }
    public UUID getRouteId() { return routeId; }
    public String getRouteDescription() { return routeDescription; }
    public Date getBookingDate() { return bookingDate; }
    public LocalTime getBookingTime() { return bookingTime; }
    public int getNumberOfPeople() { return numberOfPeople; }
    public double getTotalPrice() { return totalPrice; }
    public String getReferenceNumber() { return referenceNumber; }
    public String getStatus() { return status; }

}
