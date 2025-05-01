package com.enigma.spring_fikri_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/public")
@RestController
public class WelcomeController {
    @GetMapping("/")
    public String welcome() {
        return "Welcome to My Aplication";
    }
    @GetMapping("/login")
    public String login() {
        return "Login";
    }
}
