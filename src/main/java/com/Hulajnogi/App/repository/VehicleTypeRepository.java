package com.Hulajnogi.App.repository;

import com.Hulajnogi.App.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    Optional<VehicleType> findByBrandAndModel(String brand, String model);

}
