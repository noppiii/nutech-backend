package com.nutech.backend.repository;

import com.nutech.backend.entity.User;
import com.nutech.backend.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByUser(User user);
    Optional<Wallet> findByUser(User user);
}
