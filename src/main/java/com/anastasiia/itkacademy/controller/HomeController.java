package com.anastasiia.itkacademy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping("/public")
    public String publicHome() {
        return "Это публичная страница ";
    }

    @GetMapping("/private")
    public String privateHome() {
        return "Это закрытая страница";
    }
}