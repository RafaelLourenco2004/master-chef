package com.example.demo.model;

public enum Unit {
    KG("Kg"),
    ML("Ml"),
    CUPS("Cups");

    private String unit;
    
    
    private Unit(String unit){
        this.unit = unit;
    };
    
    public String getUnit() {
        return unit;
    }

}
