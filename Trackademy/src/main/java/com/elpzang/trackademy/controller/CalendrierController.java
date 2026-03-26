package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.entite.Evenement;
import com.elpzang.trackademy.service.EtudiantService;
import com.elpzang.trackademy.service.EvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/calendrier")
public class CalendrierController {

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping
    public String calendrier(Model model,
                             @RequestParam(required = false) Integer annee,
                             @RequestParam(required = false) Integer mois,
                             @AuthenticationPrincipal UserDetails userDetails) {
        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        LocalDate now = LocalDate.now();
        int a = (annee != null) ? annee : now.getYear();
        int m = (mois != null) ? mois : now.getMonthValue();

        model.addAttribute("activePage", "calendrier");
        model.addAttribute("evenements", evenementService.findByMoisEtudiant(a, m, etudiant));
        model.addAttribute("annee", a);
        model.addAttribute("mois", m);
        return "calendrier";
    }

    @PostMapping("/add")
    public String addEvenement(
            @RequestParam String titre,
            @RequestParam String date,
            @RequestParam String type,
            @RequestParam(required = false) String description,
            @AuthenticationPrincipal UserDetails userDetails) {

        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        Evenement evt = new Evenement();
        evt.setTitre(titre);
        evt.setDate(LocalDate.parse(date));
        evt.setType(type);
        evt.setDescription(description);
        evt.setEtudiant(etudiant);
        evenementService.save(evt);

        LocalDate d = LocalDate.parse(date);
        return "redirect:/calendrier?annee=" + d.getYear() + "&mois=" + d.getMonthValue();
    }

    @PostMapping("/delete/{id}")
    public String deleteEvenement(@PathVariable Long id,
                                  @RequestParam(required = false) Integer annee,
                                  @RequestParam(required = false) Integer mois) {
        evenementService.deleteById(id);
        LocalDate now = LocalDate.now();
        int a = (annee != null) ? annee : now.getYear();
        int m = (mois != null) ? mois : now.getMonthValue();
        return "redirect:/calendrier?annee=" + a + "&mois=" + m;
    }
}
