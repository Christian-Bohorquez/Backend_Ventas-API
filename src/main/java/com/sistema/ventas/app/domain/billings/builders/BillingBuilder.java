package com.sistema.ventas.app.domain.billings.builders;

import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.bookings.models.Booking;

import java.util.Date;
import java.util.UUID;

public class BillingBuilder {
    private UUID _id;
    private String _identification;
    private String _customerName;
    private String _customerEmail;
    private Booking _booking;
    private Date _billingDate;
    private UUID _paymentId;
    private double _totalPrice;

    public BillingBuilder() { }

    public BillingBuilder withId(UUID id) {
        this._id = id;
        return this;
    }

    public BillingBuilder withIdentification(String identification) {
        this._identification = identification;
        return this;
    }

    public BillingBuilder withCustomerName(String customerName) {
        this._customerName = customerName;
        return this;
    }

    public BillingBuilder withCustomerEmail(String customerEmail) {
        this._customerEmail = customerEmail;
        return this;
    }

    public BillingBuilder withBooking(Booking booking) {
        this._booking = booking;
        return this;
    }

    public BillingBuilder withBillingDate(Date billingDate) {
        this._billingDate = billingDate;
        return this;
    }

    public BillingBuilder withPaymentId(UUID paymentId) {
        this._paymentId = paymentId;
        return this;
    }

    public BillingBuilder withTotalPrice(double totalPrice) {
        this._totalPrice = totalPrice;
        return this;
    }

    public Billing build() {
        return Billing.create(
            _id,
            _identification,
            _customerName,
            _customerEmail,
            _booking,
            _billingDate,
            _paymentId,
            _totalPrice
        );
    }
}