package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.service.CoursService;
import com.elpzang.trackademy.service.DevoirService;
import com.elpzang.trackademy.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CoursService coursService;

    @Autowired
    private DevoirService devoirService;

    @Autowired
    private EtudiantService etudiantService;

    @ModelAttribute
    public void globalAttributes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            model.addAttribute("sidebarNbCours", 0L);
            model.addAttribute("sidebarNbDevoirs", 0L);
            return;
        }
        Etudiant etudiant = etudiantService.findByEmail(userDetails.getUsername());
        if (etudiant == null) {
            model.addAttribute("sidebarNbCours", 0L);
            model.addAttribute("sidebarNbDevoirs", 0L);
            return;
        }
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("sidebarNbCours",   coursService.countByEtudiant(etudiant));
        model.addAttribute("sidebarNbDevoirs", devoirService.countPendingByEtudiant(etudiant));
    }
}
