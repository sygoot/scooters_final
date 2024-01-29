package com.Hulajnogi.App.service;

import com.Hulajnogi.App.model.VehicleType;
import com.Hulajnogi.App.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleTypeService {

    private final VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public List<VehicleType> findAll() {
        return vehicleTypeRepository.findAll();
    }
    public List<VehicleType> findAllVehicleTypes() {
        return vehicleTypeRepository.findAll();
    }

    public VehicleType findVehicleTypeById(Long id) {
        return vehicleTypeRepository.findById(id).orElse(null);
    }

    public VehicleType saveVehicleType(VehicleType vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }

    public void deleteVehicleType(Long id) {
        vehicleTypeRepository.deleteById(id);
    }

    public ServiceResult addVehicleType(VehicleType vehicleType) {
        Optional<VehicleType> existingVehicleType = vehicleTypeRepository.findByBrandAndModel(vehicleType.getBrand(), vehicleType.getModel());
        if (existingVehicleType.isPresent()) {
            return new ServiceResult(false, "Model pojazdu o tej marce i modelu już istnieje.");
        }
        vehicleTypeRepository.save(vehicleType);
        return new ServiceResult(true, "Model pojazdu dodany pomyślnie.");
    }
}
