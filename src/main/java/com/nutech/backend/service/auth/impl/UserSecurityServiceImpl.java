package com.nutech.backend.service.auth.impl;

import com.nutech.backend.entity.User;
import com.nutech.backend.repository.UserRepository;
import com.nutech.backend.security.AuthenticationUser;
import com.nutech.backend.service.auth.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found!"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new  AuthenticationUser(user, authorities);
    }
}