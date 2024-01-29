package com.Hulajnogi.App.security;

import com.Hulajnogi.App.enums.UserRole;
import com.Hulajnogi.App.model.Address;
import com.Hulajnogi.App.model.Customer;
import com.Hulajnogi.App.model.Employee;
import com.Hulajnogi.App.model.User;
import com.Hulajnogi.App.service.UserService;
import com.Hulajnogi.App.enums.Position;
import com.Hulajnogi.App.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.time.LocalDate;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(antMatcher("/**")).permitAll()
                        //.requestMatchers(antMatcher("/admin")).hasRole("ADMIN")
                        //.requestMatchers(antMatcher("/api/rentals/rent")).authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin()
                // Konfiguracja logowania
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
                .and()


        //Konfiguracja wylogowania
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void get() {
//        Address a1 = new Address("Przemyśl", "Krucza", "3", "37-700");
//        User customerUser = new User("CustomerFName", "CustomeLName", "customer@gmail.com", "111222333",
//                "customer", passwordEncoder().encode("customer"), LocalDate.now(), UserRole.CUSTOMER, a1);
//        Customer c1 = new Customer("00258/20/1862", 123455667L, customerUser);
//        userService.addCustomer(c1);
//
//        Address a2 = new Address("Przemyśl", "Krucza", "3", "37-700");
//
//        User adminUser = new User("admin", "admin", "admin@gmail.com", "111222333",
//                "admin", passwordEncoder().encode("admin"), LocalDate.now(), UserRole.ADMIN, a2);
//        userService.saveUser(adminUser);
//
//        Address userAddress = new Address("Przemyśl", "Krucza", "3", "37-700");
//
//        User employeeUser = new User("EmployeeFName", "EmployeeLName", "employee@gmail.com", "111222333",
//                "employee", passwordEncoder().encode("employee"), LocalDate.now(), UserRole.EMPLOYEE, userAddress);
//        Employee e1 = new Employee(Position.X, employeeUser, 5000.0); // Przykładowa wartość dla wynagrodzenia
//        userService.addEmployee(e1);
//
//    }


}