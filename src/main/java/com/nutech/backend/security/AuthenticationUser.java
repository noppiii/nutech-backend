package com.nutech.backend.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class AuthenticationUser extends org.springframework.security.core.userdetails.User {

    private final com.nutech.backend.entity.User userDetails;

    public AuthenticationUser(com.nutech.backend.entity.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.userDetails = user;
    }
}