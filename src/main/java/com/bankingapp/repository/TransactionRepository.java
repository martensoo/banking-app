package com.bankingapp.repository;

import com.bankingapp.model.Account;
import com.bankingapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findAllByAccountFromOrAccountTo(Account accountFrom, Account accountTo);
}
