package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Cours;
import com.elpzang.trackademy.entite.Devoir;
import com.elpzang.trackademy.service.CoursService;
import com.elpzang.trackademy.service.DevoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/devoirs")
public class DevoirController {

    @Autowired
    private DevoirService devoirService;

    @Autowired
    private CoursService coursService;

    @GetMapping
    public String listDevoirs(Model model) {
        model.addAttribute("activePage", "devoirs");
        model.addAttribute("devoirs", devoirService.findAll());
        model.addAttribute("cours", coursService.findAll());
        model.addAttribute("nouveauDevoir", new Devoir());
        return "devoirs";
    }

    @PostMapping("/add")
    public String addDevoir(
            @RequestParam String titre,
            @RequestParam(required = false) Long coursId,
            @RequestParam(required = false) String dateRemise,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "PLANIFIE") String statut) {

        Devoir devoir = new Devoir();
        devoir.setTitre(titre);
        devoir.setDescription(description);
        devoir.setStatut(statut);

        if (dateRemise != null && !dateRemise.isEmpty()) {
            devoir.setDateRemise(LocalDate.parse(dateRemise));
        }

        if (coursId != null) {
            Cours cours = coursService.findById(coursId);
            devoir.setCours(cours);
        }

        devoirService.save(devoir);
        return "redirect:/devoirs";
    }

    @PostMapping("/delete/{id}")
    public String deleteDevoir(@PathVariable Long id) {
        devoirService.deleteById(id);
        return "redirect:/devoirs";
    }

    @PostMapping("/statut/{id}")
    public String changerStatut(@PathVariable Long id, @RequestParam String statut) {
        devoirService.changerStatut(id, statut);
        return "redirect:/devoirs";
    }
}
