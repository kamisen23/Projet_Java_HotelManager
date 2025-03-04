package com.hotel.model;

import java.time.LocalDateTime;

public class FinancialTransaction {
    private int id;
    private String type; // INCOME or EXPENSE
    private double amount;
    private String description;
    private LocalDateTime transactionDate;
    private String category;

    public FinancialTransaction() {}

    public FinancialTransaction(int id, String type, double amount, String description, 
                              LocalDateTime transactionDate, String category) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.category = category;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
