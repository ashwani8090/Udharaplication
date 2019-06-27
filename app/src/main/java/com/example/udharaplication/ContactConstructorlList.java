package com.example.udharaplication;

public class ContactConstructorlList {

    private String Phone,Date,Name;

    public ContactConstructorlList() {
    }

    public ContactConstructorlList(String phone, String date, String name) {
        Phone = phone;
        Date = date;
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
