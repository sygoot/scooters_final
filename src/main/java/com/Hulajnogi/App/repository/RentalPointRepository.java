package com.Hulajnogi.App.repository;

import com.Hulajnogi.App.model.RentalPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalPointRepository extends JpaRepository<RentalPoint, Long> {
    // Tutaj możesz dodać metody do wyszukiwania punktów wynajmu, np. na podstawie lokalizacji
}
