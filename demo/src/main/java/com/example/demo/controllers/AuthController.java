package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.AuthRequest;
import com.example.demo.auth.Authentication;
import com.example.demo.auth.exceptions.UnmatchingUserCredentialsException;
import com.example.demo.model.dtos.UserDto;
import com.example.demo.model.exceptions.EntityNotFoundException;
import com.example.demo.model.exceptions.LoginAlreadyExistsException;
import com.example.demo.model.usecases.SignUpUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SignUpUseCase signUp;

    @Autowired
    private Authentication auth;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@Valid @RequestBody UserDto user) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserDto newUser = signUp.signUp(user);
            response.put("user", newUser);
            return ResponseEntity.ok().body(response);
        } catch (LoginAlreadyExistsException e) {
            response.put("error_message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signIn(@Valid @RequestBody AuthRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String authToken = auth.getAuthenticationToken(request);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                    .body(null);
        } catch (EntityNotFoundException e) {
            response.put("error_message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (UnmatchingUserCredentialsException e) {
            response.put("error_message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
