package com.sistema.ventas.app.infrastructure.data.entities;

import com.sistema.ventas.app.infrastructure.common.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "booking")
public class BookingEntity extends BaseEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private RouteEntity route;

    @Column(nullable = false)
    private Date bookingDate;

    @Column(nullable = false)
    private LocalTime bookingTime;

    @Column(nullable = false)
    private int numberOfPeople;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private String referenceNumber;

    @Column(nullable = false, length = 100)
    private String status;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public ClientEntity getClient() { return client; }
    public void setClient(ClientEntity client) { this.client = client; }
    public RouteEntity getRoute() { return route; }
    public void setRoute(RouteEntity route) { this.route = route; }
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public LocalTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalTime bookingTime) { this.bookingTime = bookingTime; }
    public int getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(int numberOfPeople) { this.numberOfPeople = numberOfPeople; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
