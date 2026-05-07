package com.paulo_motors.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paulo_motors.demo.entities.enums.CarStatus;
import com.paulo_motors.demo.entities.enums.MotorType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_cars")
public class Car implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MotorType motor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarStatus status;

    @Column(nullable = false)
    private BigDecimal pricePerDay;

    @JsonIgnore
    @OneToMany(mappedBy = "car")
    private List<Rental> rentals = new ArrayList<>();

    public Car() {}

    public Car(UUID id, String model, String brand, String color, MotorType motor, CarStatus status, BigDecimal pricePerDay) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.motor = motor;
        this.status = status;
        this.pricePerDay = pricePerDay;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public MotorType getMotor() { return motor; }
    public void setMotor(MotorType motor) { this.motor = motor; }
    public CarStatus getStatus() { return status; }
    public void setStatus(CarStatus status) { this.status = status; }
    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }
    public List<Rental> getRentals() { return rentals; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}