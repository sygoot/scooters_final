package com.Hulajnogi.App.controller;

import com.Hulajnogi.App.model.Customer;
import com.Hulajnogi.App.model.User;
import com.Hulajnogi.App.service.CustomerService;
import com.Hulajnogi.App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;

    @Autowired
    public CustomerController(CustomerService customerService, UserService userService) {
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findCustomerById(id);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer customer = customerService.findCustomerById(id);
        if (customer != null) {
            // Ustawianie pól klienta
            return ResponseEntity.ok(customerService.saveCustomer(customer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/details/{userId}")
    public String showCustomerDetails(@PathVariable Long userId, Model model) {
        User user = userService.findUserById(userId);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("completedRentalsCount", customerService.countCompletedRentals(userId));
            model.addAttribute("rentals", customerService.findRentalsByUserId(userId));
            return "details"; // Nazwa szablonu Thymeleaf
        }
        return "redirect:/api/customers/list";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
