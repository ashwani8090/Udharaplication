package com.example.udharaplication;

public class ContactConstructorlList {

    public String Phone,Name;

    public ContactConstructorlList() {
    }

    public ContactConstructorlList(String phone,  String name) {
        Phone = phone;
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
