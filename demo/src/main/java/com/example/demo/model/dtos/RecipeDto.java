package com.example.demo.model.dtos;

import java.util.List;

import lombok.Getter;

@Getter
public class RecipeDto {
    
    private String name;
    private List<RecipeLineDto> lines;
}
