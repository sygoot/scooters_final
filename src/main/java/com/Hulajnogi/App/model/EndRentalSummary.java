package com.Hulajnogi.App.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndRentalSummary {
    private Rental rental;
    private long rentalDurationSeconds; // Nowe pole
    private double rentalPricePerMinute;
    private double totalPrice;
    private String rentalDurationFormatted;
    private String rentalStartFormatted;
    private String rentalEndFormatted;

    public EndRentalSummary() {
    }

    public EndRentalSummary(Rental rental, long rentalDurationSeconds, double rentalPricePerMinute, double totalPrice,
                            String rentalDurationFormatted, String rentalStartFormatted, String rentalEndFormatted) {
        this.rental = rental;
        this.rentalDurationSeconds = rentalDurationSeconds;
        this.rentalPricePerMinute = rentalPricePerMinute;
        this.totalPrice = totalPrice;
        this.rentalDurationFormatted = rentalDurationFormatted;
        this.rentalStartFormatted = rentalStartFormatted;
        this.rentalEndFormatted = rentalEndFormatted;
    }
}
