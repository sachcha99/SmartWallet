package com.example.finance;

public class Income {
    String id;
    String income;
    String incategory;

    public Income(){

    }

    public Income(String id, String income, String incategory) {
        this.id = id;
        this.income = income;
        this.incategory = incategory;
    }

    public String getId() {
        return id;
    }

    public String getIncome() {
        return income;
    }

    public String getIncategory() {
        return incategory;
    }
}
