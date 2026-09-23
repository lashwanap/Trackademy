package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.service.EmailService;
import com.elpzang.trackademy.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class AuthController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Value("${app.base-url}")
    private String baseUrl;

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

    /* ── Mot de passe oublié ───────────────────────────── */

    @GetMapping("/mot-de-passe-oublie")
    public String motDePasseOublieForm() {
        return "mot-de-passe-oublie";
    }

    @PostMapping("/mot-de-passe-oublie")
    public String motDePasseOublie(@RequestParam String email, RedirectAttributes redirectAttributes) {
        Etudiant etudiant = etudiantService.findByEmail(email);

        if (etudiant != null) {
            String token = UUID.randomUUID().toString();
            etudiant.setResetToken(token);
            etudiant.setResetTokenExpiration(LocalDateTime.now().plusHours(1));
            etudiantService.save(etudiant);

            String lien = baseUrl + "/reinitialiser-mot-de-passe?token=" + token;
            emailService.envoyerLienReinitialisation(etudiant.getEmail(), lien);
        }

        // Même message que le compte existe ou non, pour ne pas révéler quels courriels sont enregistrés
        redirectAttributes.addFlashAttribute("succes",
                "Si un compte existe pour cette adresse, un lien de réinitialisation vient d'être envoyé.");
        return "redirect:/mot-de-passe-oublie";
    }

    /* ── Réinitialisation du mot de passe ─────────────────*/

    @GetMapping("/reinitialiser-mot-de-passe")
    public String reinitialiserForm(@RequestParam String token, Model model, RedirectAttributes redirectAttributes) {
        Etudiant etudiant = etudiantService.findByResetToken(token);

        if (etudiant == null || etudiant.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("erreur", "Ce lien de réinitialisation est invalide ou a expiré.");
            return "redirect:/mot-de-passe-oublie";
        }

        model.addAttribute("token", token);
        return "reinitialiser-mot-de-passe";
    }

    @PostMapping("/reinitialiser-mot-de-passe")
    public String reinitialiser(
            @RequestParam String token,
            @RequestParam String motDePasse,
            @RequestParam String motDePasseConfirm,
            RedirectAttributes redirectAttributes) {

        Etudiant etudiant = etudiantService.findByResetToken(token);

        if (etudiant == null || etudiant.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("erreur", "Ce lien de réinitialisation est invalide ou a expiré.");
            return "redirect:/mot-de-passe-oublie";
        }

        if (!motDePasse.equals(motDePasseConfirm)) {
            redirectAttributes.addFlashAttribute("erreur", "Les mots de passe ne correspondent pas.");
            return "redirect:/reinitialiser-mot-de-passe?token=" + token;
        }

        if (motDePasse.length() < 6) {
            redirectAttributes.addFlashAttribute("erreur", "Le mot de passe doit contenir au moins 6 caractères.");
            return "redirect:/reinitialiser-mot-de-passe?token=" + token;
        }

        etudiant.setMotDePasse(passwordEncoder.encode(motDePasse));
        etudiant.setResetToken(null);
        etudiant.setResetTokenExpiration(null);
        etudiantService.save(etudiant);

        redirectAttributes.addFlashAttribute("succes", "Mot de passe réinitialisé avec succès ! Vous pouvez vous connecter.");
        return "redirect:/connexion";
    }
}
