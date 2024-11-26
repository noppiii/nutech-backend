package com.nutech.backend.repository;

import com.nutech.backend.entity.User;
import com.nutech.backend.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByUser(User user);
}
