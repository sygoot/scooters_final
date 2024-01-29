package com.Hulajnogi.App.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home"; // Dostępna dla wszystkich, niezalogowanych użytkowników
    }

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userRole", auth.getAuthorities().toString()); // Przekazuje rolę użytkownika do widoku
        return "dashboard"; // Dostępna tylko dla zalogowanych użytkowników
    }
}
