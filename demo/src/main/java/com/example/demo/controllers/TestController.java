package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.adapters.ChefAgent;

@RestController
@RequestMapping("/teste")
public class TestController {

    @Autowired
    private ChefAgent chefAgent;
    
    @GetMapping()
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello World");
    }
}
