package com.Hulajnogi.App.service;

import com.Hulajnogi.App.enums.VehicleStatus;
import com.Hulajnogi.App.model.Rental;
import com.Hulajnogi.App.model.Vehicle;
import com.Hulajnogi.App.repository.RentalRepository;
import com.Hulajnogi.App.repository.VehicleRepository;
import com.Hulajnogi.App.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;


    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeRepository vehicleTypeRepository, RentalRepository rentalRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.rentalRepository = rentalRepository;
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public List<Rental> getVehicleRentals(Long vehicleId) {
        // Poprawione wywołanie metody z rentalRepository
        return rentalRepository.findByVehicleId(vehicleId);
    }

    public void updateVehicleStatus(Long vehicleId, VehicleStatus newStatus) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
        if (vehicle != null) {
            vehicle.setStatus(newStatus);
            vehicleRepository.save(vehicle);
        }
    }

    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }





    public Vehicle findVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
