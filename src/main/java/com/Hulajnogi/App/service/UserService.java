package com.Hulajnogi.App.service;

import com.Hulajnogi.App.model.Customer;
import com.Hulajnogi.App.model.Employee;
import com.Hulajnogi.App.model.User;
import com.Hulajnogi.App.repository.CustomerRepository;
import com.Hulajnogi.App.repository.EmployeeRepository;
import com.Hulajnogi.App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       CustomerRepository customerRepository,
                       EmployeeRepository employeeRepository,
                       @Lazy    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserInfo(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika o ID: " + updatedUser.getId()));

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        return userRepository.save(existingUser);
    }

    // Metoda do zmiany hasła użytkownika
    public boolean changeUserPassword(User user, String oldPassword, String newPassword, String confirmPassword) {
        if (passwordEncoder.matches(oldPassword, user.getPassword()) && newPassword.equals(confirmPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void addCustomer(Customer customer){
        userRepository.save(customer.getUser());
        customerRepository.save(customer);
    }

    public void addEmployee(Employee employee){
        userRepository.save(employee.getUser());
        employeeRepository.save(employee);
    }


    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean userExists(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username).orElseThrow();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    // Inne metody biznesowe związane z użytkownikiem
}
