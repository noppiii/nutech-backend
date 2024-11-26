package com.nutech.backend.service.auth.impl;

import com.nutech.backend.constant.ErrorCode;
import com.nutech.backend.entity.User;
import com.nutech.backend.exception.CustomException;
import com.nutech.backend.exception.DuplicateException;
import com.nutech.backend.exception.InvalidException;
import com.nutech.backend.payload.request.auth.LoginRequest;
import com.nutech.backend.payload.request.auth.RegisterRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.auth.LoginResponse;
import com.nutech.backend.repository.UserRepository;
import com.nutech.backend.security.JwtToken;
import com.nutech.backend.security.JwtTokenProvider;
import com.nutech.backend.service.auth.AuthService;
import com.nutech.backend.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public CustomSuccessResponse<String> register(RegisterRequest registerRequest) {
        userRepository.findByEmail(registerRequest.getEmail()).ifPresent(it -> {
            throw new DuplicateException(ErrorCode.ALREADY_REGISTER);
        });

        User user = User.of(
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getName());

        userRepository.save(user);
        return new CustomSuccessResponse<>("200", "Berhasil melakukan registrasi", null);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication;

        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String authorities = authentication.getAuthorities().stream()
                .map(a -> "ROLE_" + a.getAuthority())
                .collect(Collectors.joining(","));
        JwtToken jwtToken = jwtTokenProvider.createJwtToken(loginRequest.getEmail(), authorities);

        return LoginResponse.of(jwtToken, user);
    }

    @Override
    public JwtToken refreshToken(String requestRefreshToken) {
        Authentication authentication = validateAndGetAuthentication(requestRefreshToken);
        String userEmail = authentication.getName();

        checkLogin(userEmail);

        String currentRefreshToken = jwtTokenUtils.getRefreshToken(userEmail);

        if(isSnatch(requestRefreshToken, currentRefreshToken) == true) {
            logout(authentication.getName());
            throw new InvalidException(ErrorCode.SNATCH_TOKEN);
        }

        return jwtTokenProvider.refreshJwtToken(authentication);
    }

    @Override
    public CustomSuccessResponse<String> logout(String email) {
        jwtTokenUtils.deleteRefreshToken(email);
        return new CustomSuccessResponse<>("200", "Berhasil logout", null);
    }

    private Authentication validateAndGetAuthentication(String requestRefreshToken){
        return  jwtTokenProvider.getAuthenticationByRefreshToken(requestRefreshToken);
    }

    private void checkLogin(String email) {
        jwtTokenProvider.checkLogin(email);
    }

    private boolean isSnatch(String requestRefreshToken, String currentRefreshToken) {
        return !currentRefreshToken.equals(requestRefreshToken);
    }
}
