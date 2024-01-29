package com.Hulajnogi.App.service;

import com.Hulajnogi.App.DTO.EmployeeRegistrationDto;
import com.Hulajnogi.App.enums.Position;
import com.Hulajnogi.App.enums.UserRole;
import com.Hulajnogi.App.model.Address;
import com.Hulajnogi.App.model.Employee;
import com.Hulajnogi.App.model.User;
import com.Hulajnogi.App.repository.EmployeeRepository;
import com.Hulajnogi.App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewEmployee(EmployeeRegistrationDto employeeDto) {
        User user = new User();
        user.setFirstName(employeeDto.getFirstName());
        user.setLastName(employeeDto.getLastName());
        user.setEmail(employeeDto.getEmail());
        user.setPhoneNumber(employeeDto.getPhoneNumber());
        user.setLogin(employeeDto.getLogin());
        user.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        user.setBirthDate(employeeDto.getBirthDate());
        user.setRole(UserRole.EMPLOYEE); // Ustawienie roli na EMPLOYEE

        Address address = new Address();
        address.setCity(employeeDto.getCity());
        address.setStreet(employeeDto.getStreet());
        address.setHuNumber(employeeDto.getHuNumber());
        address.setPostalCode(employeeDto.getPostalCode());
        user.setAddress(address);

        Employee employee = new Employee();
        employee.setUser(user); // Powiązanie pracownika z użytkownikiem
        employee.setPosition(Position.X); // Przypisanie stanowiska (przy założeniu, że position jest enumem)
        employee.setSalary(employeeDto.getSalary()); // Przypisanie pensji

        userRepository.save(user); // Zapisanie użytkownika
        employeeRepository.save(employee); // Zapisanie pracownika

        return user;
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

}
