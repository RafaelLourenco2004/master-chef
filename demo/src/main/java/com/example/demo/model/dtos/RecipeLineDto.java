package com.example.demo.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecipeLineDto {
    
    private String ingredient;
    private Double amount;
    private String unit;

}
