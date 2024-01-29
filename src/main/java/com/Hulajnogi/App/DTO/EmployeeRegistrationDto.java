package com.Hulajnogi.App.DTO;

import com.Hulajnogi.App.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegistrationDto {

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
    private String huNumber;
    private String postalCode;

    // Dane Pracownika
    private Position position;
    private double salary;
}

