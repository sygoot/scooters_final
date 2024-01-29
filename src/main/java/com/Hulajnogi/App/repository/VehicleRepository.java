package com.Hulajnogi.App.repository;

import com.Hulajnogi.App.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAvailableScootersByRentalPointId(Long rentalPointId);


}
