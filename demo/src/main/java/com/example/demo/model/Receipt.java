package com.example.demo.model;

import java.util.List;

import lombok.Getter;

@Getter
public class Receipt {
    
    private List<ReceiptLine> lines;
}
