package com.example.udharaplication;

public class ContactConstructorlList {

    public String Phone,Name;
    public Integer Pk;

    public ContactConstructorlList() {
    }

    public ContactConstructorlList(Integer pk,String phone,  String name) {
        Phone = phone;
        Name = name;
        Pk=pk;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Integer getPk() {
        return Pk;
    }

    public void setPk(Integer pk) {
        Pk = pk;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
