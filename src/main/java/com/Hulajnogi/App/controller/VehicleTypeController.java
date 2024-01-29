package com.Hulajnogi.App.controller;

import com.Hulajnogi.App.model.VehicleType;
import com.Hulajnogi.App.service.ServiceResult;
import com.Hulajnogi.App.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/vehicleType")
public class VehicleTypeController {

    private final VehicleTypeService vehicleTypeService;

    @Autowired
    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleType>> getAllVehicleTypes() {
        List<VehicleType> vehicleTypes = vehicleTypeService.findAllVehicleTypes();
        return ResponseEntity.ok(vehicleTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleType> getVehicleTypeById(@PathVariable Long id) {
        VehicleType vehicleType = vehicleTypeService.findVehicleTypeById(id);
        return vehicleType != null ? ResponseEntity.ok(vehicleType) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<VehicleType> createVehicleType(@RequestBody VehicleType vehicleType) {
        VehicleType savedVehicleType = vehicleTypeService.saveVehicleType(vehicleType);
        return ResponseEntity.ok(savedVehicleType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleType> updateVehicleType(@PathVariable Long id, @RequestBody VehicleType vehicleTypeDetails) {
        VehicleType updatedVehicleType = vehicleTypeService.findVehicleTypeById(id);
        if (updatedVehicleType != null) {
            updatedVehicleType.setBrand(vehicleTypeDetails.getBrand());
            updatedVehicleType.setModel(vehicleTypeDetails.getModel());
            updatedVehicleType.setType(vehicleTypeDetails.getType());
            updatedVehicleType.setCapacity(vehicleTypeDetails.getCapacity());
            updatedVehicleType.setPrice(vehicleTypeDetails.getPrice());
            vehicleTypeService.saveVehicleType(updatedVehicleType);
            return ResponseEntity.ok(updatedVehicleType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable Long id) {
        vehicleTypeService.deleteVehicleType(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/addModel")
    public String showAddVehicleTypeForm(Model model) {
        model.addAttribute("vehicleType", new VehicleType());
        return "addModel"; // Nazwa szablonu Thymeleaf
    }

    @PostMapping("/addModel")
    public String addVehicleType(@ModelAttribute VehicleType vehicleType, RedirectAttributes redirectAttributes, Model model) {
        ServiceResult result = vehicleTypeService.addVehicleType(vehicleType);

        if (result.isSuccess()) {
            return "redirect:/dashboard"; // Przekierowanie po pomyślnym dodaniu
        } else {
            model.addAttribute("errorMessage", result.getMessage());
            model.addAttribute("vehicleType", vehicleType); // Zachowaj dane wprowadzone przez użytkownika
            return "addModel"; // Powrót do formularza z zachowaniem danych
        }
    }

}
