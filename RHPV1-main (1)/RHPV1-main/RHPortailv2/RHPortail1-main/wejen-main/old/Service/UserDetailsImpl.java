package com.example.version1.Service;

import com.example.version1.Model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.version1.Model.Role;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final Utilisateur utilisateur;

    public UserDetailsImpl(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return utilisateur.getMotDePasse();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can assign roles dynamically based on the user's role in the database
        return Collections.singletonList(new SimpleGrantedAuthority(utilisateur.getRole()));
    }

    // Implement other required methods of UserDetails interface (e.g., isAccountNonExpired, isAccountNonLocked, etc.)
}
