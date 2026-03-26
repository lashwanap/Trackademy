package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.service.BudgetService;
import com.elpzang.trackademy.service.CoursService;
import com.elpzang.trackademy.service.DevoirService;
import com.elpzang.trackademy.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    private CoursService coursService;

    @Autowired
    private DevoirService devoirService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping("/")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("nbCours", coursService.count());
        model.addAttribute("nbDevoirs", devoirService.countPending());
        model.addAttribute("prochainDevoirs", devoirService.findPending());
        BigDecimal solde = budgetService.solde();
        model.addAttribute("solde", solde);

        // Pourcentage pour la barre de progression (objectif 500 $)
        double pct = solde.doubleValue() / 500.0 * 100.0;
        if (pct < 0)   pct = 0;
        if (pct > 100) pct = 100;
        model.addAttribute("budgetPourcent", (int) pct);

        // Date formattée pour le dashboard
        String dateAujourdhui = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH));
        model.addAttribute("dateAujourdhui", dateAujourdhui);

        return "dashboard";
    }
}
