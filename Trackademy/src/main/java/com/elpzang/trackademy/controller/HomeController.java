package com.elpzang.trackademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "page";
    }

    @GetMapping("/calendrier")
    public String calendrier() {
        return "calendrier";
    }

    @GetMapping("/")
    public String dashboard() {
        return "page";
    }
}
