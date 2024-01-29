package com.Hulajnogi.App.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;
    private String drivingLicenseNumber;
    private Long cardNumber;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "customer")
    private Set<Payment> payments;

    public Customer(String drivingLicenseNumber, Long cardNumber, User user) {
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.cardNumber = cardNumber;
        this.user = user;
    }
}
