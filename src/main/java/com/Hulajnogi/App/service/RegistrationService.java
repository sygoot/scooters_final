package com.Hulajnogi.App.service;

import com.Hulajnogi.App.DTO.UserRegistrationDto;
import com.Hulajnogi.App.enums.UserRole;
import com.Hulajnogi.App.repository.UserRepository;
import com.Hulajnogi.App.model.Address;
import com.Hulajnogi.App.model.Customer;
import com.Hulajnogi.App.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public User registerNewUser(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setLogin(registrationDto.getLogin());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setBirthDate(registrationDto.getBirthDate());
        user.setRole(UserRole.CUSTOMER);

        Address address = new Address();
        address.setCity(registrationDto.getCity());
        address.setStreet(registrationDto.getStreet());
        address.setHuNumber(registrationDto.getHuNumber());
        address.setPostalCode(registrationDto.getPostalCode());
        user.setAddress(address);

        Customer customer = new Customer();
        customer.setDrivingLicenseNumber(registrationDto.getDrivingLicenseNumber());
        customer.setCardNumber(registrationDto.getCardNumber());
        customer.setUser(user); // Ustawić powiązanie z User
        userService.addCustomer(customer);

        // Zapisz użytkownika, adres i klienta w bazie danych
        return userRepository.save(user);
    }
}
