package com.nutech.backend.repository;

import com.nutech.backend.entity.Transaction;
import com.nutech.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

     List<Transaction> findByUser(User user);
}
