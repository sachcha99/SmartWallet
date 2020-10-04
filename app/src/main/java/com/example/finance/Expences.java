package com.example.finance;

public class Expences {
    String id;
    String expen;
    String encategory;

    public Expences(){

    }

    public Expences(String id, String expen, String encategory) {
        this.id = id;
        this.expen = expen;
        this.encategory = encategory;
    }

    public String getId() {
        return id;
    }

    public String getExpen() {
        return expen;
    }

    public String getEncategory() {
        return encategory;
    }
}
