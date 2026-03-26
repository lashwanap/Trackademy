package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.entite.Transaction;
import com.elpzang.trackademy.service.BudgetService;
import com.elpzang.trackademy.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping
    public String budget(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        BigDecimal revenus   = budgetService.totalRevenusParEtudiant(etudiant);
        BigDecimal depenses  = budgetService.totalDepensesParEtudiant(etudiant);
        BigDecimal solde     = budgetService.soldeParEtudiant(etudiant);

        // Calcul du pourcentage dépensé (pour la barre de progression)
        int pourcentage = 0;
        if (revenus.compareTo(BigDecimal.ZERO) > 0) {
            pourcentage = depenses.multiply(BigDecimal.valueOf(100))
                                  .divide(revenus, 0, java.math.RoundingMode.HALF_UP)
                                  .intValue();
            if (pourcentage > 100) pourcentage = 100;
        }

        model.addAttribute("activePage", "budget");
        model.addAttribute("transactions", budgetService.findByEtudiant(etudiant));
        model.addAttribute("revenus", revenus);
        model.addAttribute("depenses", depenses);
        model.addAttribute("solde", solde);
        model.addAttribute("pourcentage", pourcentage);
        return "budget";
    }

    @PostMapping("/add")
    public String addTransaction(
            @RequestParam String description,
            @RequestParam BigDecimal montant,
            @RequestParam String type,
            @RequestParam String categorie,
            @RequestParam(required = false) String date,
            @AuthenticationPrincipal UserDetails userDetails) {

        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        Transaction t = new Transaction();
        t.setDescription(description);
        t.setMontant(montant);
        t.setType(type);
        t.setCategorie(categorie);
        t.setDate(date != null && !date.isEmpty() ? LocalDate.parse(date) : LocalDate.now());
        t.setEtudiant(etudiant);
        budgetService.save(t);
        return "redirect:/budget";
    }

    @PostMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        budgetService.deleteById(id);
        return "redirect:/budget";
    }
}
