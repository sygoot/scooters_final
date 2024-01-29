package com.Hulajnogi.App.controller;


import com.Hulajnogi.App.DTO.RentalRequestDto;
import com.Hulajnogi.App.model.EndRentalSummary;
import com.Hulajnogi.App.model.Rental;
import com.Hulajnogi.App.model.RentalPoint;
import com.Hulajnogi.App.model.User;
import com.Hulajnogi.App.repository.RentalPointRepository;
import com.Hulajnogi.App.repository.UserRepository;
import com.Hulajnogi.App.service.RentalPointService;
import com.Hulajnogi.App.service.UserService;
import com.Hulajnogi.App.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final RentalPointRepository rentalPointRepository;

    private final UserRepository userRepository;
    private final RentalPointService rentalPointService;
    private final UserService userService;

    @Autowired
    public RentalController(RentalService rentalService, RentalPointRepository rentalPointRepository, UserRepository userRepository, RentalPointService rentalPointService, UserService userService) {
        this.rentalService = rentalService;
        this.rentalPointRepository = rentalPointRepository;
        this.userRepository = userRepository;
        this.rentalPointService = rentalPointService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        return ResponseEntity.ok(rentalService.saveRental(rental));
    }

    @GetMapping("/listRentals")
    public String listRentals(Model model) {
        List<Rental> rentals = rentalService.getAllRentals();
        model.addAttribute("rentals", rentals);
        return "rentalList";
    }


    @GetMapping("/selectRentalPoint")
    public String showRentalPoints(Model model) {
        List<RentalPoint> rentalPoints = rentalPointRepository.findAll();
        model.addAttribute("rentalPoints", rentalPoints);
        return "selectRentalPoint";
    }

    @PostMapping("/selectRentalPoint")
    public String selectRentalPoint(@RequestParam("rentalPointId") Long rentalPointId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("rentalPointId", rentalPointId);
        return "redirect:/api/rentals/availableScooters";
    }

    @GetMapping("/rentalPointsData")
    @ResponseBody
    public List<RentalPoint> getRentalPointsData() {
        return rentalPointService.getAllRentalPointsWithAvailableVehicles();
    }

    @GetMapping("/rent")
    public String showRentVehicleForm(Model model) {
        model.addAttribute("rentalRequest", new RentalRequestDto());
        return "rentVehicle";
    }

    @PostMapping("/rent")
    public String rentVehicle(@RequestParam("idVehicle") Long idVehicle,
                              @RequestParam("startRentalPointId") Long startRentalPointId,
                              RedirectAttributes redirectAttributes) {
        try {
            Rental rental = rentalService.rentVehicle(idVehicle, startRentalPointId); // Przekaż ID punktu początkowego do metody rentVehicle
            redirectAttributes.addAttribute("rentalId", rental.getId());
            return "redirect:/api/rentals/summary/{rentalId}";
        } catch (Exception e) {
            return "redirect:/error"; // Przekierowanie do strony błędu
        }
    }

    @GetMapping("/summary/{rentalId}")
    public String rentalSummary(@PathVariable Long rentalId, Model model) {
        Rental rental = rentalService.getRentalById(rentalId);
        List<RentalPoint> rentalPoints = rentalPointService.findAll();
        if (rental != null && rental.isActive()) {
            model.addAttribute("rental", rental);
            model.addAttribute("rentalPoints", rentalPoints);
            return "rentalSummary";
        }
        // Przekierowanie w przypadku błędu (np. wypożyczenie nie istnieje lub jest nieaktywne)
        return "redirect:/error";
    }

    @PostMapping("/endRental")
    public String endRental(@RequestParam Long rentalId,
                            @RequestParam Long returnRentalPointId,
                            RedirectAttributes redirectAttributes) {
        try {
            rentalService.endRental(rentalId, returnRentalPointId); // Przekaż ID punktu zwrotu do metody endRental
            redirectAttributes.addAttribute("rentalId", rentalId);
            return "redirect:/api/rentals/endRentalSummary";
        } catch (Exception e) {
            return "redirect:/error"; // Przekierowanie do strony błędu
        }
    }

    @GetMapping("/endRentalSummary")
    public String endRentalSummary(@RequestParam Long rentalId, Model model) {
        Rental rental = rentalService.getRentalById(rentalId);

        if (rental != null) {
            LocalDateTime rentalStart = rental.getRentalStart();
            LocalDateTime rentalEnd = rental.getRentalEnd();

            Duration duration = Duration.between(rentalStart, rentalEnd);
            long totalSeconds = duration.getSeconds();

            double pricePerMinute = rental.getVehicle().getPrice(); // Pobierz cenę z VehicleType

            // Oblicz całkowitą cenę na podstawie pełnych minut
            double totalPrice = Math.ceil((double) totalSeconds / 60) * pricePerMinute;

            // Oblicz sformatowany czas trwania w formacie mm:ss
            long minutes = totalSeconds / 60;
            long seconds = totalSeconds % 60;
            String rentalDurationFormatted = String.format("%02d:%02d", minutes, seconds);

            // Sformatuj daty rozpoczęcia i zakończenia
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String rentalStartFormatted = rentalStart.format(formatter);
            String rentalEndFormatted = rentalEnd.format(formatter);

            EndRentalSummary summary = new EndRentalSummary(rental, totalSeconds, pricePerMinute, totalPrice,
                    rentalDurationFormatted, rentalStartFormatted, rentalEndFormatted);

            model.addAttribute("summary", summary);

            return "endRentalSummary";
        }

        // Obsługa, gdy wypożyczenie nie istnieje
        return "redirect:/error";
    }

    @GetMapping("/myRentals")
    public String myRentals(Model model) {
        // Pobierz aktualnie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Pobierz użytkownika na podstawie nazwy użytkownika (loginu)
        User user = userService.findByLogin(currentUsername);

        // Pobierz listę wynajmów użytkownika
        List<Rental> rentals = rentalService.getRentalsByUserId(user.getId());

        model.addAttribute("rentals", rentals);

        return "myRentals";
    }

    // Dodaj tutaj inne endpointy, np. do zakończenia wypożyczenia
}
