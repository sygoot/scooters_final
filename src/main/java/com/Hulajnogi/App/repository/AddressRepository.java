package com.Hulajnogi.App.repository;

import com.Hulajnogi.App.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // Dodatkowe metody związane z wyszukiwaniem adresów, jeśli potrzebne
}
