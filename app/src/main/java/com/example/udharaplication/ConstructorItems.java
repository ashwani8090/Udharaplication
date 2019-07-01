package com.example.udharaplication;

public class ConstructorItems {

    //ID INTEGER PRIMARY KEY AUTOINCREMENT,DATES,ITEM_NAME,PHONE,AMOUNT INTEGER,DESCRIPTION, FOREIGN KEY(PHONE) REFERENCES Contacts(Phone) )");


    private Integer ID;
    private String DATES,ITEM_NAME,PHONE;
    private Integer AMOUNT;
    private String DESCRIPTION;



    public ConstructorItems() {
    }


    public ConstructorItems(Integer ID, String DATES, String ITEM_NAME, String PHONE, Integer AMOUNT, String DESCRIPTION) {
        this.ID = ID;
        this.DATES = DATES;
        this.ITEM_NAME = ITEM_NAME;
        this.PHONE = PHONE;
        this.AMOUNT = AMOUNT;
        this.DESCRIPTION = DESCRIPTION;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDATES() {
        return DATES;
    }

    public void setDATES(String DATES) {
        this.DATES = DATES;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public Integer getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(Integer AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
}
