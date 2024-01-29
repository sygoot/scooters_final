package com.Hulajnogi.App.repository;

import com.Hulajnogi.App.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Dodatkowe metody związane z wyszukiwaniem płatności, jeśli potrzebne
}
