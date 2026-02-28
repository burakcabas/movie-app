package com.mcp.movieapp.exception;

import io.swagger.v3.oas.annotations.Hidden; // YENI EKLENDI
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Hidden // SİHİRLİ DOKUNUŞ: Swagger'a bu sinifi analiz etmemesini soyler.
@RestControllerAdvice(basePackages = "com.mcp.movieapp.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParams(MissingServletRequestParameterException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("hata", "Eksik parametre gönderdiniz.");
        response.put("detay", ex.getParameterName() + " parametresi zorunludur.");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}