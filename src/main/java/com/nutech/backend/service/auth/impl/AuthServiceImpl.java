package com.nutech.backend.service.auth.impl;

import com.nutech.backend.constant.ErrorCode;
import com.nutech.backend.entity.User;
import com.nutech.backend.exception.DuplicateException;
import com.nutech.backend.payload.request.auth.RegisterRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.repository.UserRepository;
import com.nutech.backend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
