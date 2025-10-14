package com.example.demo.adapters;

import java.util.List;

import com.example.demo.model.Receipt;

public interface ChefAgent {
    
    List<Receipt> get_receipts(String prompt) throws Exception;
}
