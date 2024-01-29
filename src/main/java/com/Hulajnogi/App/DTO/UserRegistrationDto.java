package com.Hulajnogi.App.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    // Dane Użytkownika
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String login;
    private String password;
    private LocalDate birthDate;

    // Dane Adresu
    private String city;
    private String street;
    private String huNumber; // Może być potrzebne jako String, jeśli zawiera literki
    private String postalCode;

    // Dane Klienta (Customer)
    private String drivingLicenseNumber;
    private Long cardNumber;

    // Gettery, settery oraz konstruktory są generowane przez Lombok
}
