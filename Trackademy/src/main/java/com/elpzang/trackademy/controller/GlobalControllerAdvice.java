package com.elpzang.trackademy.controller;

import com.elpzang.trackademy.service.CoursService;
import com.elpzang.trackademy.service.DevoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CoursService coursService;

    @Autowired
    private DevoirService devoirService;

    @ModelAttribute
    public void globalAttributes(Model model) {
        model.addAttribute("sidebarNbCours",   coursService.count());
        model.addAttribute("sidebarNbDevoirs", devoirService.countPending());
    }
}
