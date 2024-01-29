package com.Hulajnogi.App.controller;

import com.Hulajnogi.App.model.User;
import com.Hulajnogi.App.service.AddressService;
import com.Hulajnogi.App.service.CustomerService;
import com.Hulajnogi.App.service.RentalService;
import com.Hulajnogi.App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final AddressService addressService;
    private final RentalService rentalService;

    private final CustomerService customerService;


    @Autowired
    public UserController(UserService userService, AddressService addressService, RentalService rentalService, CustomerService customerService) {
        this.userService = userService;
        this.addressService = addressService;
        this.rentalService = rentalService;
        this.customerService = customerService;
    }


    @GetMapping("/myAccount")
    public String myAccount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userService.findByLogin(currentUserName);

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("totalRides", rentalService.countUserRides(user.getId()));
            model.addAttribute("rentals", rentalService.findUserRides(user.getId()));
        }

        return "myAccount";
    }

    @GetMapping("/editAccount")
    public String editAccount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userService.findByLogin(currentUserName);

        if (user != null) {
            model.addAttribute("user", user);
            return "editAccount"; // nazwa szablonu Thymeleaf dla edycji użytkownika
        } else {
            return "redirect:/myAccount";
        }
    }

    // Metoda do aktualizacji danych użytkownika
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@ModelAttribute User updatedUser) {
        userService.updateUserInfo(updatedUser);
        return "redirect:/myAccount";
    }

    // Metoda do zmiany hasła
    @PostMapping("/changeUserPassword")
    public String changeUserPassword(@RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("confirmPassword") String confirmPassword,
                                     Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userService.findByLogin(currentUserName);

        if (currentUser != null && userService.changeUserPassword(currentUser, oldPassword, newPassword, confirmPassword)) {
            return "redirect:/myAccount";
        } else {
            model.addAttribute("passwordError", "Błąd przy zmianie hasła.");
            model.addAttribute("user", currentUser);
            return "editAccount";
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
