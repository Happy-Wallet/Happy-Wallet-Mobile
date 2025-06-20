package com.example.happy_wallet_mobile.Model;

public class Transaction {
    private String id;
    private String title;
    private String amount;
    private String date;
    private String category;
    private String note;
    private String type; // "income" or "expenditure"

    public Transaction() {}

    public Transaction(String title, String amount, String date, String category, String note, String type) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.note = note;
        this.type = type;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
