package com.nutech.backend.repository;

import com.nutech.backend.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

     @Query(value = "SELECT * FROM transactions WHERE user_id = :userId", nativeQuery = true)
     List<Transaction> findByUser(@Param("userId") Long userId);

     @Query(value = "SELECT * FROM transactions WHERE user_id = :userId AND type IN :types ORDER BY created_at DESC", nativeQuery = true)
     Page<Transaction> findByUserAndTypeInOrderByCreatedAtDesc(@Param("userId") Long userId, @Param("types") List<String> types, Pageable pageable);
}