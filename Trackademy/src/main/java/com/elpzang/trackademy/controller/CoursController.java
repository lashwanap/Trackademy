package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Cours;
import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.service.CoursService;
import com.elpzang.trackademy.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cours")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping
    public String listCours(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        model.addAttribute("activePage", "cours");
        model.addAttribute("cours", coursService.findByEtudiant(etudiant));
        model.addAttribute("nouveauCours", new Cours());
        return "cours";
    }

    @PostMapping("/add")
    public String addCours(@ModelAttribute Cours cours, @AuthenticationPrincipal UserDetails userDetails) {
        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        cours.setEtudiant(etudiant);
        coursService.save(cours);
        return "redirect:/cours";
    }

    @PostMapping("/delete/{id}")
    public String deleteCours(@PathVariable Long id) {
        coursService.deleteById(id);
        return "redirect:/cours";
    }
}
