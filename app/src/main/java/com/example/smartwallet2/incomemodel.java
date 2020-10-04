package com.example.smartwallet2;

import java.io.Serializable;

public class incomemodel implements Serializable {
    private String id;
    private String addcategory;
    private String catrgoryType;

    private incomemodel(){

    }

    public incomemodel(String id, String addcategory, String catrgoryType) {
        this.id = id;
        this.addcategory = addcategory;
        this.catrgoryType = catrgoryType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddcategory(String addcategory) {
        this.addcategory = addcategory;
    }

    public void setCatrgoryType(String catrgoryType) {
        this.catrgoryType = catrgoryType;
    }

    public String getId() {
        return id;
    }

    public String getAddcategory() {
        return addcategory;
    }

    public String getCatrgoryType() {
        return catrgoryType;
    }
}
