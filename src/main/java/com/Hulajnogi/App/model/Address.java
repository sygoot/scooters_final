package com.Hulajnogi.App.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAddress;
    @Column(nullable = true)
    private String city;
    @Column(nullable = true)
    private String street;
    @Column(nullable = true)
    private String huNumber;
    @Column(nullable = true)
    private String postalCode;

    public Address(String city, String street, String huNumber, String postalCode) {
        this.city = city;
        this.street = street;
        this.huNumber = huNumber;
        this.postalCode = postalCode;
    }


    // Konstruktory, gettery, settery itd.
}
