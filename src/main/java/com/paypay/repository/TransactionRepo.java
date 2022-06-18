package com.paypay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paypay.model.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT * FROM `transaction` WHERE id_user=? ORDER BY date DESC", nativeQuery = true)
    List<Transaction> findByUser(Integer idUser);

    @Query(value = "SELECT * FROM `transaction` WHERE id_user=? ORDER BY date DESC LIMIT 4;", nativeQuery = true)
    List<Transaction> findByUserLimit4(Integer idUser);

    @Query(value = "SELECT * FROM `transaction` WHERE id_user=? ORDER BY date DESC LIMIT 5;", nativeQuery = true)
    List<Transaction> findByUserLimit5(Integer idUser);

    @Query(value = "SELECT * FROM `transaction` WHERE id_user=? ORDER BY date DESC LIMIT 7;", nativeQuery = true)
    List<Transaction> findByUserLimit7(Integer idUser);

    @Query(value = "SELECT nominal FROM `transaction` WHERE transaction_type='Kredit' AND id_user=? ORDER BY date DESC LIMIT 1;", nativeQuery = true)
    Long getKredit(Integer idUser);

    @Query(value = "SELECT nominal FROM `transaction` WHERE transaction_type='Debit' AND id_user=? ORDER BY date DESC LIMIT 1;", nativeQuery = true)
    Long getDebit(Integer idUser);
}
