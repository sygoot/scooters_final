package com.Hulajnogi.App.controller;

import com.Hulajnogi.App.enums.VehicleStatus;
import com.Hulajnogi.App.model.Vehicle;
import com.Hulajnogi.App.service.RentalPointService;
import com.Hulajnogi.App.service.VehicleService;
import com.Hulajnogi.App.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleTypeService vehicleTypeService;
    private final RentalPointService rentalPointService;

    @Autowired
    public VehicleController(VehicleService vehicleService, VehicleTypeService vehicleTypeService, RentalPointService rentalPointService) {
        this.vehicleService = vehicleService;
        this.vehicleTypeService = vehicleTypeService;
        this.rentalPointService = rentalPointService;
    }

    @GetMapping("/listVehicles")
    public String listVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "listVehicles"; // Nazwa szablonu Thymeleaf
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        return ResponseEntity.ok(vehicles);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.findVehicleById(id);
        return vehicle != null ? ResponseEntity.ok(vehicle) : ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicleDetails) {
        Vehicle updatedVehicle = vehicleService.findVehicleById(id);
        if (updatedVehicle != null) {
            updatedVehicle.setStatus(vehicleDetails.getStatus());
            updatedVehicle.setVehicleRange(vehicleDetails.getVehicleRange());
            updatedVehicle.setVehicleType(vehicleDetails.getVehicleType());
            vehicleService.saveVehicle(updatedVehicle);
            return ResponseEntity.ok(updatedVehicle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/addVehicles")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("vehicleTypes", vehicleTypeService.findAll());
        model.addAttribute("rentalPoints", rentalPointService.findAll());
        return "addVehicles";
    }

    @PostMapping("/addVehicles")
    public String addVehicle(@ModelAttribute Vehicle vehicle) {
        vehicleService.addVehicle(vehicle);
        return "redirect:/dashboard";
    }

    @GetMapping("/details/{id}")
    public String vehicleDetails(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        if (vehicle != null) {
            model.addAttribute("vehicle", vehicle);
            model.addAttribute("vehicleRentals", vehicleService.getVehicleRentals(id));
            return "vehicleDetails";
        }
        return "redirect:/vehicles/list"; // Przekierowanie, jeśli pojazd nie istnieje
    }

    @PostMapping("/updateStatus")
    public String updateVehicleStatus(@RequestParam Long vehicleId, @RequestParam VehicleStatus newStatus) {
        vehicleService.updateVehicleStatus(vehicleId, newStatus);
        return "redirect:/dashboard"; // Przekierowanie na /dashboard po udanej aktualizacji
    }
}
