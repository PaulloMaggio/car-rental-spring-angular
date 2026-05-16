package com.paulo_motors.demo.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_rentals")
public class Rental implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Instant startDate;
    private Instant endDate;
    private BigDecimal priceAtRental;
    private BigDecimal totalValue;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public Rental() {}

    public Rental(UUID id, Instant startDate, Instant endDate, BigDecimal priceAtRental, BigDecimal totalValue, Client client, Car car) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceAtRental = priceAtRental;
        this.totalValue = totalValue;
        this.client = client;
        this.car = car;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Instant getStartDate() { return startDate; }
    public void setStartDate(Instant startDate) { this.startDate = startDate; }
    public Instant getEndDate() { return endDate; }
    public void setEndDate(Instant endDate) { this.endDate = endDate; }
    public BigDecimal getPriceAtRental() { return priceAtRental; }
    public void setPriceAtRental(BigDecimal priceAtRental) { this.priceAtRental = priceAtRental; }
    public BigDecimal getTotalValue() { return totalValue; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
}