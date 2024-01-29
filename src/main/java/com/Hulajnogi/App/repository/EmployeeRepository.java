package com.Hulajnogi.App.repository;

import com.Hulajnogi.App.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Metody związane z wyszukiwaniem pracowników, jeśli potrzebne
}
