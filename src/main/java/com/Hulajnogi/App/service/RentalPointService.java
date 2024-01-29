package com.Hulajnogi.App.service;

import com.Hulajnogi.App.model.RentalPoint;
import com.Hulajnogi.App.repository.RentalPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalPointService {
    private final RentalPointRepository rentalPointRepository;

    @Autowired
    public RentalPointService(RentalPointRepository rentalPointRepository) {
        this.rentalPointRepository = rentalPointRepository;
    }

    public List<RentalPoint> findAll() {
        return rentalPointRepository.findAll();
    }


    public RentalPoint saveRentalPoint(RentalPoint rentalPoint) {
        return rentalPointRepository.save(rentalPoint);
    }



    public List<RentalPoint> getAllRentalPointsWithAvailableVehicles() {
        List<RentalPoint> rentalPoints = rentalPointRepository.findAll();
        // Możesz tu dodatkowo przetworzyć listę, jeśli potrzebujesz
        return rentalPoints;
    }
}
