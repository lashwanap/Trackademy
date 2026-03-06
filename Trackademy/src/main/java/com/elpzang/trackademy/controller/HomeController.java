package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.service.BudgetService;
import com.elpzang.trackademy.service.CoursService;
import com.elpzang.trackademy.service.DevoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("nbCours", coursService.count());
        model.addAttribute("nbDevoirs", devoirService.countPending());
        model.addAttribute("prochainDevoirs", devoirService.findPending());
        model.addAttribute("solde", budgetService.solde());

        // Date formattée pour le dashboard
        String dateAujourdhui = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH));
        model.addAttribute("dateAujourdhui", dateAujourdhui);

        return "dashboard";
    }
}
