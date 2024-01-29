package com.Hulajnogi.App.security;

import com.Hulajnogi.App.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Przypisanie roli użytkownika (np. ADMIN, CUSTOMER, itd.)
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        authorities.add(authority);

        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin(); // lub getEmail(), w zależności od tego, co używasz jako identyfikator
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Konto nie wygasło
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Konto nie jest zablokowane
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Poświadczenia nie wygasły
    }

    @Override
    public boolean isEnabled() {
        return true; // Konto jest aktywne
    }

    // Implementacja pozostałych metod z UserDetails
}

