package com.example.demo.model;

import java.util.List;
import lombok.Getter;

@Getter
public class Recipe {
    
    private String name;
    private List<RecipeLine> lines;
}
