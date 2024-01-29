package com.Hulajnogi.App.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "start_rental_point_id")
    private RentalPoint startRentalPoint;

    @ManyToOne
    @JoinColumn(name = "end_rental_point_id")
    private RentalPoint returnRentalPoint;

    private LocalDateTime rentalStart;
    private LocalDateTime rentalEnd;
    private String status; // np. "aktywne", "zakończone"

    public Rental(boolean isActive,
                  User user, Vehicle vehicle,
                  RentalPoint startRentalPoint,
                  RentalPoint returnRentalPoint,
                  LocalDateTime rentalStart,
                  LocalDateTime rentalEnd,
                  String status) {
        this.isActive = isActive;
        this.user = user;
        this.vehicle = vehicle;
        this.startRentalPoint = startRentalPoint;
        this.returnRentalPoint = returnRentalPoint;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.status = status;
    }

    public Rental(boolean isActive, User user, Vehicle vehicle, RentalPoint startRentalPoint, RentalPoint returnRentalPoint, String status) {
        this.isActive = isActive;
        this.user = user;
        this.vehicle = vehicle;
        this.startRentalPoint = startRentalPoint;
        this.returnRentalPoint = returnRentalPoint;
        this.status = status;
    }
}