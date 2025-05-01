package com.enigma.spring_fikri_demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/private")
@RestController
public class PrivateController {
    @GetMapping("/logout")
    public String logout() {
        return "Harus Login terlebih dahulu!";
    }
    @GetMapping("/customer")
    public String customerHome() {
        return "Ini halaman private! anda harus login";
    }
    @GetMapping("/admin")
    public String adminHome() {
        return "Ini Halaman Admin, butuh login sebagai Admin!";
    }
    @GetMapping("/about")
    public String about() {
        return "Welcome to My Aplication About";
    }
}

