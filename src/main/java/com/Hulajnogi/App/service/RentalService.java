package com.Hulajnogi.App.service;


import com.Hulajnogi.App.enums.VehicleStatus;
import com.Hulajnogi.App.model.Rental;
import com.Hulajnogi.App.model.RentalPoint;
import com.Hulajnogi.App.model.User;
import com.Hulajnogi.App.model.Vehicle;
import com.Hulajnogi.App.repository.RentalPointRepository;
import com.Hulajnogi.App.repository.RentalRepository;
import com.Hulajnogi.App.repository.UserRepository;
import com.Hulajnogi.App.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    private final RentalPointRepository rentalPointRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, VehicleRepository vehicleRepository, RentalPointRepository rentalPointRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.rentalPointRepository = rentalPointRepository;
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }


    public Rental rentVehicle(Long idVehicle,Long startRentalPointId) throws Exception {
        // Pobierz aktualnie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // Pobierz użytkownika na podstawie nazwy użytkownika (loginu)
        User user = userRepository.findByLogin(currentUserName)
                .orElseThrow(() -> new Exception("Nie znaleziono użytkownika: " + currentUserName));

        // Pobierz pojazd z bazy danych
        Vehicle vehicle = vehicleRepository.findById(idVehicle)
                .orElseThrow(() -> new Exception("Nie znaleziono pojazdu o id: " + idVehicle));

        // Sprawdź, czy pojazd jest dostępny do wypożyczenia
        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new Exception("Pojazd nie jest dostępny do wypożyczenia.");
        }

        // Utwórz nowe wypożyczenie
        Rental rental = new Rental();
        rental.setVehicle(vehicle);
        rental.setUser(user);
        rental.setStartRentalPoint(rentalPointRepository.findById(startRentalPointId)
                .orElseThrow(() -> new Exception("Nie znaleziono punktu wynajmu o id: " + startRentalPointId)));
        rental.setRentalStart(LocalDateTime.now());
        rental.setActive(true);
        rental.setStatus("ACTIVE");

        // Zapisz wypożyczenie w bazie danych
        rental = rentalRepository.save(rental);

        // Zaktualizuj status pojazdu jako wypożyczony
        vehicle.setStatus(VehicleStatus.IN_USE);
        vehicleRepository.save(vehicle);

        return rental; // Zwróć obiekt Rental
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long rentalId) {
        // Zakładając, że RentalRepository jest już zdefiniowane i ma metodę findById
        return rentalRepository.findById(rentalId).orElse(null);
    }


    public void endRental(Long rentalId, Long returnRentalPointId) {
        Rental rental = rentalRepository.findById(rentalId).orElse(null);
        if (rental != null && rental.isActive()) {
            rental.setActive(false);
            rental.setRentalEnd(LocalDateTime.now());
            rental.setStatus("ENDED");
            Vehicle vehicle = rental.getVehicle();
            rental.setReturnRentalPoint(rentalPointRepository.findById(returnRentalPointId)
                    .orElseThrow(() -> new IllegalStateException("Nie znaleziono punktu zwrotu o id: " + returnRentalPointId)));
            rentalRepository.save(rental);
            vehicle.setRentalPoint(rentalPointRepository.findById(returnRentalPointId)
                    .orElseThrow(() -> new IllegalStateException("Nie znaleziono punktu zwrotu o id: " + returnRentalPointId)));
            vehicleRepository.save(vehicle);
            vehicle.setStatus(VehicleStatus.AVAILABLE);
        } else {
            throw new IllegalStateException("Wypożyczenie nie istnieje lub zostało już zakończone");
        }
    }

    public List<RentalPoint> getAllRentalPoints() {
        return rentalPointRepository.findAll();
    }

    public List<Rental> getRentalsByUserId(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

    public List<Vehicle> getAvailableScooters(Long rentalPointId) {
        // Zakładając, że VehicleRepository posiada metodę do wyszukiwania dostępnych hulajnóg w danym punkcie wynajmu
        return vehicleRepository.findAvailableScootersByRentalPointId(rentalPointId);
    }

    public int countUserRides(Long userId) {
        return rentalRepository.countByUserId(userId);
    }

    public List<Rental> findUserRides(Long userId) {
        return rentalRepository.findByUserId(userId);
    }
}
    // Dodaj tutaj inne metody, które będą potrzebne do obsługi logiki wypożyczeń


