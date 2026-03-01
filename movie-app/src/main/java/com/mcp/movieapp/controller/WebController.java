package com.mcp.movieapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // DİKKAT: @RestController değil! Çünkü bu sınıf JSON değil, HTML sayfası döndürecek.
public class WebController {

    // Ana sayfaya (localhost:8080) girildiğinde bu metot çalışacak
    @GetMapping("/")
    public String homePage() {
        // Spring Boot, "templates" klasörünün içinde "index.html" adında bir dosya arayıp onu ekrana basar
        return "index";
    }
}