package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProfilController {

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping("/profil")
    public String profilPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("activePage", "profil");

        List<String> avatars = List.of(
            "https://i.pravatar.cc/160?img=1",
            "https://i.pravatar.cc/160?img=5",
            "https://i.pravatar.cc/160?img=11",
            "https://i.pravatar.cc/160?img=16",
            "https://i.pravatar.cc/160?img=20",
            "https://i.pravatar.cc/160?img=25",
            "https://i.pravatar.cc/160?img=32",
            "https://i.pravatar.cc/160?img=36",
            "https://i.pravatar.cc/160?img=44",
            "https://i.pravatar.cc/160?img=47",
            "https://i.pravatar.cc/160?img=57",
            "https://i.pravatar.cc/160?img=64"
        );
        model.addAttribute("avatars", avatars);
        return "profil";
    }

    @PostMapping("/profil/avatar")
    public String changerAvatar(@RequestParam String avatarUrl,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        etudiant.setPhotoProfil(avatarUrl);
        etudiantService.save(etudiant);
        redirectAttributes.addFlashAttribute("succes", "Photo de profil mise à jour !");
        return "redirect:/profil";
    }
}
