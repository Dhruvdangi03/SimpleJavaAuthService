package com.AuthService.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("check")
public class CheckController {
    @GetMapping
    public ResponseEntity<String> check(){
        return ResponseEntity.ok("If You are seeing this Text then it is WORKING");
    }
}
