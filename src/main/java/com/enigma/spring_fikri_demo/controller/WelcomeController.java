package com.enigma.spring_fikri_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/")
    public String welcome() {
        return "Welcome to My Aplication";
    }
    @GetMapping("/about")
    public String about() {
        return "Welcome to My Aplication About";
    }
}
