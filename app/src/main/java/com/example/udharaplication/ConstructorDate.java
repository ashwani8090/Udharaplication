package com.example.udharaplication;

public class ConstructorDate {


    private String DATE,PHONE;
    private Integer RECIEVED,LEFTP,TOTAL,PK;
    private String PAID;

    public ConstructorDate() {
    }

    public ConstructorDate(String DATE, String PHONE, Integer RECIEVED, Integer LEFTP, Integer TOTAL, String PAID,Integer PK) {
        this.DATE = DATE;
        this.PHONE = PHONE;
        this.RECIEVED = RECIEVED;
        this.LEFTP = LEFTP;
        this.TOTAL = TOTAL;
        this.PAID = PAID;
        this.PK=PK;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public Integer getRECIEVED() {
        return RECIEVED;
    }

    public void setRECIEVED(Integer RECIEVED) {
        this.RECIEVED = RECIEVED;
    }

    public Integer getLEFTP() {
        return LEFTP;
    }

    public void setLEFTP(Integer LEFTP) {
        this.LEFTP = LEFTP;
    }

    public Integer getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(Integer TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getPAID() {
        return PAID;
    }

    public void setPAID(String PAID) {
        this.PAID = PAID;
    }

    public Integer getPK() {
        return PK;
    }

    public void setPK(Integer PK) {
        this.PK = PK;
    }
}
