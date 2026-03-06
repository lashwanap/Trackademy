package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Transaction;
import com.elpzang.trackademy.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public String budget(Model model) {
        BigDecimal revenus   = budgetService.totalRevenus();
        BigDecimal depenses  = budgetService.totalDepenses();
        BigDecimal solde     = budgetService.solde();

        // Calcul du pourcentage dépensé (pour la barre de progression)
        int pourcentage = 0;
        if (revenus.compareTo(BigDecimal.ZERO) > 0) {
            pourcentage = depenses.multiply(BigDecimal.valueOf(100))
                                  .divide(revenus, 0, java.math.RoundingMode.HALF_UP)
                                  .intValue();
            if (pourcentage > 100) pourcentage = 100;
        }

        model.addAttribute("activePage", "budget");
        model.addAttribute("transactions", budgetService.findAll());
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
            @RequestParam(required = false) String date) {

        Transaction t = new Transaction();
        t.setDescription(description);
        t.setMontant(montant);
        t.setType(type);
        t.setCategorie(categorie);
        t.setDate(date != null && !date.isEmpty() ? LocalDate.parse(date) : LocalDate.now());
        budgetService.save(t);
        return "redirect:/budget";
    }

    @PostMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        budgetService.deleteById(id);
        return "redirect:/budget";
    }
}
