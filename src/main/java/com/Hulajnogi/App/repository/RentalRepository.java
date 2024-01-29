package com.Hulajnogi.App.repository;

import com.Hulajnogi.App.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserId(Long userId);
    int countByUserId(Long userId);

    List<Rental> findByVehicleId(Long vehicleId);

    @Query("SELECT COUNT(r) FROM Rental r WHERE r.user.id = :userId AND r.status = 'zakończone'")
    int countCompletedRentalsForUser(Long userId);
}