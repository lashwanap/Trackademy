package com.elpzang.trackademy.repository;

import com.elpzang.trackademy.entite.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByType(String type);

    @Query("SELECT COALESCE(SUM(t.montant), 0) FROM Transaction t WHERE t.type = 'REVENU'")
    BigDecimal sumRevenus();

    @Query("SELECT COALESCE(SUM(t.montant), 0) FROM Transaction t WHERE t.type = 'DEPENSE'")
    BigDecimal sumDepenses();

    List<Transaction> findAllByOrderByDateDesc();
}
