package com.nutech.backend.repository;

import com.nutech.backend.entity.User;
import com.nutech.backend.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM wallets WHERE user_id = :userId", nativeQuery = true)
    boolean existsByUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM wallets WHERE user_id = :userId", nativeQuery = true)
    Optional<Wallet> findByUser(@Param("userId") Long userId);
}