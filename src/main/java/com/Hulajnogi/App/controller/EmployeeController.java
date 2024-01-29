package com.Hulajnogi.App.controller;

import com.Hulajnogi.App.DTO.EmployeeRegistrationDto;
import com.Hulajnogi.App.model.Customer;
import com.Hulajnogi.App.model.Employee;
import com.Hulajnogi.App.model.Rental;
import com.Hulajnogi.App.service.CustomerService;
import com.Hulajnogi.App.service.EmployeeService;
import com.Hulajnogi.App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CustomerService customerService, UserService userService, PasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee != null) {
            // Ustawianie pól pracownika
            return ResponseEntity.ok(employeeService.saveEmployee(employee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listCustomers")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.findAllCustomers();
        model.addAttribute("customers", customers);
        return "listCustomers"; // Nazwa szablonu Thymeleaf
    }


    @GetMapping("/details/{idCustomer}")
    public String showCustomerDetails(@PathVariable Long idCustomer, Model model) {
        Customer customer = customerService.findCustomerById(idCustomer);
        if (customer != null) {
            model.addAttribute("customer", customer);
            List<Rental> rentals = customerService.findRentalsByUserId(customer.getUser().getId());
            model.addAttribute("rentals", rentals);
            return "customerDetails";
        }
        return "redirect:/api/customers/listCustomers";
    }

    @GetMapping("/listEmployees")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.findAllEmployees();
        model.addAttribute("employees", employees);
        return "listEmployees";
    }

    @GetMapping("/registerEmployee")
    public String showEmployeeRegistrationForm(Model model) {
        model.addAttribute("employeeDto", new EmployeeRegistrationDto());
        return "registerEmployee";
    }

    @PostMapping("/registerEmployee")
    public String registerEmployeeAccount(@ModelAttribute("employeeDto") EmployeeRegistrationDto employeeDto) {
        employeeService.registerNewEmployee(employeeDto);
        return "registrationEmployee_success";
    }
}
