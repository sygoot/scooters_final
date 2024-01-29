package com.Hulajnogi.App.model;

import com.Hulajnogi.App.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status; // Zakładając, że VehicleStatus to enum

    private int vehicleRange;

    @ManyToOne
    @JoinColumn(name="id_model")
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "rental_point_id")
    @JsonIgnore //testowo
    private RentalPoint rentalPoint;


    public Vehicle(VehicleStatus status, int vehicleRange, VehicleType vehicleType, RentalPoint rentalPoint) {
        this.status = status;
        this.vehicleRange = vehicleRange;
        this.vehicleType = vehicleType;
        this.rentalPoint = rentalPoint;
    }

    public double getPrice() {
        return this.vehicleType.getPrice();
    }

}
