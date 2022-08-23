package com.training.lab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Spring Boot Home..!!";
    }

    @GetMapping("/greet")
    public String greet() {
        return "Greetings to Spring Boot Home..!!";
    }

    @GetMapping("/greet/{name}")
    public String greetByName(@PathVariable String name) {
        return "Welcome " + name + " on Spring Boot Home...!!!";
    }
}

