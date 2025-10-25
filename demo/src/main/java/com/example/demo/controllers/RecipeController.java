package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Utils.JsonParsing;
import com.example.demo.adapters.agents.Agent;
import com.example.demo.adapters.filters.FilterManager;
import com.example.demo.adapters.filters.FilterManagerImpl;
import com.example.demo.adapters.filters.RecipeFilter;
import com.example.demo.model.Recipe;
import com.example.demo.model.exceptions.InvalidApiResponseException;
import com.example.demo.model.exceptions.InvalidUserPromptException;

@RestController
@RequestMapping("/receitas")
public class RecipeController {

    @Autowired
    private Agent chefAgent;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> requestRecipe(@RequestBody String request) {
        Map<String, Object> response = new HashMap<>();

        try {
            FilterManager manager = new FilterManagerImpl(
                    List.of(new RecipeFilter(request)));

            if (!manager.applyFilters()) {
                response.put("error_message", "Prompt não condiz com o propósito do modelo");
                response.put("content", null);
                return ResponseEntity.badRequest().body(response);
            }

            String jsonResponse = chefAgent.get_receipts(request);
            List<Recipe> recipes = JsonParsing.parseRecipe(jsonResponse, Recipe.class);
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
