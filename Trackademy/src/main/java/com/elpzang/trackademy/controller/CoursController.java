package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.entite.Cours;
import com.elpzang.trackademy.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cours")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @GetMapping
    public String listCours(Model model) {
        model.addAttribute("activePage", "cours");
        model.addAttribute("cours", coursService.findAll());
        model.addAttribute("nouveauCours", new Cours());
        return "cours";
    }

    @PostMapping("/add")
    public String addCours(@ModelAttribute Cours cours) {
        coursService.save(cours);
        return "redirect:/cours";
    }

    @PostMapping("/delete/{id}")
    public String deleteCours(@PathVariable Long id) {
        coursService.deleteById(id);
        return "redirect:/cours";
    }
}
