package com.example.version1.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_EMPLOYEE,
    ROLE_ADMIN,
    ROLE_HUMAN_RESOURCE;


    @Override
    public String getAuthority() {
        return name().replace("ROLE_", "");
    }
}
