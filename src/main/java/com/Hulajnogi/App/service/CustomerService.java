package com.Hulajnogi.App.service;

import com.Hulajnogi.App.model.Customer;
import com.Hulajnogi.App.model.Rental;
import com.Hulajnogi.App.repository.CustomerRepository;
import com.Hulajnogi.App.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, RentalRepository rentalRepository) {
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public int countCompletedRentals(Long userId) {
        return rentalRepository.countCompletedRentalsForUser(userId);
    }

    public List<Rental> findRentalsByUserId(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

}
