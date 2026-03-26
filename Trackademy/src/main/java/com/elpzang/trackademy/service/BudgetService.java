package com.elpzang.trackademy.service;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.entite.Transaction;
import com.elpzang.trackademy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findAll() {
        return transactionRepository.findAllByOrderByDateDesc();
    }

    public BigDecimal totalRevenus() {
        BigDecimal val = transactionRepository.sumRevenus();
        return val != null ? val : BigDecimal.ZERO;
    }

    public BigDecimal totalDepenses() {
        BigDecimal val = transactionRepository.sumDepenses();
        return val != null ? val : BigDecimal.ZERO;
    }

    public BigDecimal solde() {
        return totalRevenus().subtract(totalDepenses());
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> findByEtudiant(Etudiant etudiant) {
        return transactionRepository.findByEtudiantOrderByDateDesc(etudiant);
    }

    public BigDecimal totalRevenusParEtudiant(Etudiant etudiant) {
        BigDecimal val = transactionRepository.sumRevenusParEtudiant(etudiant);
        return val != null ? val : BigDecimal.ZERO;
    }

    public BigDecimal totalDepensesParEtudiant(Etudiant etudiant) {
        BigDecimal val = transactionRepository.sumDepensesParEtudiant(etudiant);
        return val != null ? val : BigDecimal.ZERO;
    }

    public BigDecimal soldeParEtudiant(Etudiant etudiant) {
        return totalRevenusParEtudiant(etudiant).subtract(totalDepensesParEtudiant(etudiant));
    }
}
