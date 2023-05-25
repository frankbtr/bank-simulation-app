package com.frank.repository;

import com.frank.dto.TransactionDTO;
import com.frank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM transactions ORDER BY transaction_date DESC LIMIT 10", nativeQuery = true)
    List<Transaction> findLast10Transaction();

    @Query("SELECT t FROM Transaction t WHERE t.sender.id = ?1 OR t.receiver.id=?1")
    List<Transaction> findTransactionListById(Long id);
}
