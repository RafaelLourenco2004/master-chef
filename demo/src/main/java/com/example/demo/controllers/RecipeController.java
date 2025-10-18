package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Utils.JsonParsing;
import com.example.demo.adapters.ChefAgent;
import com.example.demo.model.Recipe;
import com.example.demo.model.exceptions.InvalidApiResponseException;
import com.example.demo.model.exceptions.InvalidUserPromptException;

@RestController
@RequestMapping("/receitas")
public class RecipeController {

    @Autowired
    private ChefAgent chefAgent;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> hello() {
        Map<String, Object> response = new HashMap<>();

        try {
            String jsonResponse = chefAgent.get_receipts("Quem foi o campeão da Copa do mundo de 2002?");
            List<Recipe> recipes = JsonParsing.parseJson(jsonResponse, Recipe.class);
            response.put("error_message", "");
            response.put("content", recipes);
            return ResponseEntity.ok(response);
        } catch (InvalidUserPromptException e) {
            response.put("error_message", e.getMessage());
            response.put("content", null);
            return ResponseEntity.badRequest().body(response);
        } catch (InvalidApiResponseException e) {
            response.put("error_message", e.getMessage());
            response.put("content", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("error_message", "Algo deu errado");
            response.put("content", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
