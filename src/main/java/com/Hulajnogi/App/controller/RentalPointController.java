package com.Hulajnogi.App.controller;

import com.Hulajnogi.App.model.Address;
import com.Hulajnogi.App.model.RentalPoint;
import com.Hulajnogi.App.model.Vehicle;
import com.Hulajnogi.App.service.RentalPointService;
import com.Hulajnogi.App.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/rentals")
public class RentalPointController {
    private final RentalPointService rentalPointService;
    private final RentalService rentalService;

    @Autowired
    public RentalPointController(RentalPointService rentalPointService, RentalService rentalService) {
        this.rentalPointService = rentalPointService;
        this.rentalService = rentalService;
    }

    @GetMapping("/rentalPoints")
    public String showRentalPoints(Model model) {
        List<RentalPoint> rentalPoints = rentalPointService.getAllRentalPointsWithAvailableVehicles();
        model.addAttribute("rentalPoints", rentalPoints);
        return "rentalPointsList"; // nazwa Twojego widoku Thymeleaf
    }

    @GetMapping("/addRentalPoint")
    public String showAddRentalPointForm(Model model) {
        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setAddress(new Address()); // Ustawienie pustego adresu
        model.addAttribute("rentalPoint", rentalPoint);
        return "addRentalPoint";
    }

    @PostMapping("/addRentalPoint")
    public String addRentalPoint(@RequestParam String name,
                                 @RequestParam String city,
                                 @RequestParam String street,
                                 @RequestParam String huNumber,
                                 @RequestParam String postalCode,
                                 @RequestParam double latitude,
                                 @RequestParam double longitude) {
        Address address = new Address(city, street, huNumber, postalCode);
        RentalPoint rentalPoint = new RentalPoint(name, address, latitude, longitude);
        rentalPointService.saveRentalPoint(rentalPoint);
        return "redirect:/dashboard";
    }


    @GetMapping("/availableScooters")
    public String showAvailableScooters(@RequestParam("rentalPointId") Long rentalPointId, Model model) {
        List<Vehicle> availableScooters = rentalService.getAvailableScooters(rentalPointId);
        model.addAttribute("availableScooters", availableScooters);
        model.addAttribute("rentalPointId", rentalPointId);
        return "availableScooters"; // Nazwa widoku do wyświetlenia dostępnych hulajnóg
    }


}
