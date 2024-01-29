package com.Hulajnogi.App.DTO;

import com.Hulajnogi.App.enums.VehicleStatus;
import com.Hulajnogi.App.enums.ScooterType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAddDto {

    //Dane VehicleType
    private String brand;

    private String model;

    @Enumerated(EnumType.STRING)
    private ScooterType vehicleType;

    private int capacity;

    private double price;


    //Dane Vehicle
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    private int range;

}
