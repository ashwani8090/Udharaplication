package com.example.udharaplication;

public class ConstructorDate {


    private String DATE,PHONE,PK;
    private Integer RECIEVED,LEFTP,TOTAL;
    private String PAID,TRANSACTIONS;

    public ConstructorDate() {
    }

    public ConstructorDate(String DATE, String PHONE, Integer RECIEVED, Integer LEFTP, Integer TOTAL, String PAID,String PK,String TRANSACTIONS) {
        this.DATE = DATE;
        this.PHONE = PHONE;
        this.RECIEVED = RECIEVED;
        this.LEFTP = LEFTP;
        this.TOTAL = TOTAL;
        this.PAID = PAID;
        this.PK=PK;
        this.TRANSACTIONS=TRANSACTIONS;
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

    public String getPK() {
        return PK;
    }

    public void setPK(String PK) {
        this.PK = PK;
    }

    public String getTRANSACTIONS() {
        return TRANSACTIONS;
    }

    public void setTRANSACTIONS(String TRANSACTIONS) {
        this.TRANSACTIONS = TRANSACTIONS;
    }
}
