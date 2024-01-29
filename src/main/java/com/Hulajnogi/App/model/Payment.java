package com.Hulajnogi.App.model;

import com.Hulajnogi.App.enums.PaymentMethod;
import com.Hulajnogi.App.enums.PaymentStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPayment;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method; // Zakładając, że PaymentMethod to enum

    private double amount;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Zakładając, że PaymentStatus to enum

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Konstruktory, gettery, settery itd.
}
