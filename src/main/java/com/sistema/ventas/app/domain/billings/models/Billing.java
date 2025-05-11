package com.sistema.ventas.app.domain.billings.models;

import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.clients.valueobjects.Email;
import com.sistema.ventas.app.domain.shared.exceptions.BusinessRuleViolationException;
import com.sistema.ventas.app.domain.shared.valueobjects.Identification;
import com.sistema.ventas.app.domain.shared.valueobjects.Name;

import java.util.Date;
import java.util.UUID;

public class Billing {
    private UUID id;
    private Identification identification;
    private String customerName;
    private Email customerEmail;
    private Booking booking;
    private Date billingDate;
    private UUID paymentId;
    private double totalPrice;

    private Billing(
            UUID id, Identification identification,
            String customerName, Email customerEmail,
            Booking booking, Date billingDate, UUID paymentId, double totalPrice ) {
        this.id = id;
        this.identification = identification;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.booking = booking;
        this.billingDate = billingDate;
        this.paymentId = paymentId;
        this.totalPrice = totalPrice;

        this.validate();
    }

    public static Billing create(
            UUID id, String identification,
            String customerName, String customerEmail,
            Booking booking, Date billingDate, UUID paymentId, double totalPrice ) {

        if (id == null)  {
            if (booking.getBookingDate().before(new Date()) || !booking.getStatus().equals(BookingStatus.RESERVADA)) {
                throw new BusinessRuleViolationException("No se puede facturar una reserva ya vencida.");
            }

            booking.setStatus(BookingStatus.FACTURADA);
            billingDate = new Date();
            totalPrice = booking.getTotalPrice() * 1.12;
        }
        if (billingDate.after(new Date())) {
            throw new BusinessRuleViolationException("No se puede facturar con una fecha futura.");
        }

        return new Billing(
            id != null ? id : UUID.randomUUID(),
            Identification.create(identification),
            customerName,
            Email.create(customerEmail),
            booking,
            billingDate,
            paymentId,
            totalPrice
        );
    }

    private void validate() {
        if (booking == null) throw new BusinessRuleViolationException("La reserva es obligatoria");
        if (totalPrice <= 0) throw new BusinessRuleViolationException("El precio total debe ser mayor a cero.");
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Identification getIdentification() { return identification; }
    public void setIdentification(Identification identification) { this.identification = identification; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public Email getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(Email customerEmail) { this.customerEmail = customerEmail; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public Date getBillingDate() { return billingDate; }
    public void setBillingDate(Date billingDate) { this.billingDate = billingDate; }
    public UUID getPaymentId() { return paymentId; }
    public void setPaymentId(UUID paymentId) { this.paymentId = paymentId; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

}
