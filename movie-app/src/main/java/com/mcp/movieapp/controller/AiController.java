package com.mcp.movieapp.controller;

import com.mcp.movieapp.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private GeminiService geminiService;

    // Eski genel sohbet ucu
    @GetMapping("/ask")
    public String askAi(@RequestParam String question) {
        return geminiService.askQuestion(question);
    }

    // YENI: Sadece veritabanindaki filmlerden tavsiye veren uc
    @GetMapping("/recommend")
    public String recommendMovie(@RequestParam String request) {
        return geminiService.recommendMovieFromDb(request);
    }
}