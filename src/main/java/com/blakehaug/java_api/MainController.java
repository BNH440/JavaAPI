package com.blakehaug.java_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/hello")
public class MainController {
    @GetMapping
    public String root() {
        return "Hello World!";
    }
}