package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* ── Connexion ─────────────────────────────────────── */

    @GetMapping("/connexion")
    public String connexion(
            @RequestParam(required = false) String erreur,
            @RequestParam(required = false) String deconnecte,
            Model model) {

        if (erreur     != null) model.addAttribute("erreur",     "Adresse courriel ou mot de passe incorrect.");
        if (deconnecte != null) model.addAttribute("deconnecte", "Vous avez été déconnecté avec succès.");
        return "connexion";
    }

    /* ── Inscription ───────────────────────────────────── */

    @GetMapping("/inscription")
    public String inscriptionForm(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        return "inscription";
    }

    @PostMapping("/inscription")
    public String inscrire(
            @RequestParam String prenom,
            @RequestParam String nom,
            @RequestParam String email,
            @RequestParam String programme,
            @RequestParam String session,
            @RequestParam String motDePasse,
            @RequestParam String motDePasseConfirm,
            RedirectAttributes redirectAttributes) {

        // Validation : mots de passe identiques
        if (!motDePasse.equals(motDePasseConfirm)) {
            redirectAttributes.addFlashAttribute("erreur", "Les mots de passe ne correspondent pas.");
            return "redirect:/inscription";
        }

        // Validation : email déjà utilisé
        if (etudiantService.findByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("erreur", "Cette adresse courriel est déjà utilisée.");
            return "redirect:/inscription";
        }

        // Création du compte
        Etudiant etudiant = new Etudiant();
        etudiant.setPrenom(prenom);
        etudiant.setNom(nom);
        etudiant.setEmail(email);
        etudiant.setProgramme(programme);
        etudiant.setSession(session);
        etudiant.setMotDePasse(passwordEncoder.encode(motDePasse));
        etudiantService.save(etudiant);

        redirectAttributes.addFlashAttribute("succes",
                "Compte créé avec succès ! Vous pouvez maintenant vous connecter.");
        return "redirect:/connexion";
    }
}
