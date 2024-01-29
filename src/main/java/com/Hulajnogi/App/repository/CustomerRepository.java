package com.Hulajnogi.App.repository;

import com.Hulajnogi.App.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Override
    Optional<Customer> findById(Long aLong);

    // Metody związane z wyszukiwaniem klientów, jeśli potrzebne
}
