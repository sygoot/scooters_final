package com.Hulajnogi.App.model;


import com.Hulajnogi.App.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "idAddress")
    private Address address;

    private double latitude;
    private double longitude;

    @OneToMany(mappedBy = "rentalPoint")
    private Set<Vehicle> vehicles; // Zestaw hulajnóg przypisanych do tego punktu


    public long getAvailableVehiclesCount() {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getStatus() == VehicleStatus.AVAILABLE)
                .count();
    }

    public RentalPoint(String name, Address address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}

