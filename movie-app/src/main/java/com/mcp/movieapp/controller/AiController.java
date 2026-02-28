package com.mcp.movieapp.controller;

import com.mcp.movieapp.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private GeminiService geminiService;

    // GÜNCELLEME: /ask -> /general-chat (Genel Yapay Zeka Sohbeti)
    @GetMapping("/general-chat")
    public String askAi(@RequestParam String question) {
        return geminiService.askQuestion(question);
    }

    // GÜNCELLEME: /recommend -> /db-chat (Veritabanı Bağlamlı RAG Sohbeti)
    @GetMapping("/db-chat")
    public String recommendMovie(@RequestParam String request) {
        return geminiService.recommendMovieFromDb(request);
    }
}