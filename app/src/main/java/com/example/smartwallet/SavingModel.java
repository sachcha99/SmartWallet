package com.example.smartwallet;

import java.io.Serializable;

public class SavingModel implements Serializable {
    private String id;
    private  String addsavingname;
    private String  addsavingamount;


    private SavingModel(){

    }

    SavingModel(String id,String addsavingname,String  addsavingamount){
        this.id = id;
        this.addsavingname = addsavingname;
        this.addsavingamount = addsavingamount;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddsavingname() {
        return addsavingname;
    }

    public void setAddsavingname(String addsavingname) {
        this.addsavingname = addsavingname;
    }

    public String getAddsavingamount() {
        return addsavingamount;
    }

    public void setAddsavingamount(String addsavingamount) {
        this.addsavingamount = addsavingamount;
    }
}
