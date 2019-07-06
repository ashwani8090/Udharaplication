package com.example.udharaplication;

public class ContactConstructorlList {

    private String Phone,Name;
    private String Pk;

    public ContactConstructorlList() {
    }

    public ContactConstructorlList(String PK,String Phone,  String Name) {
        this.Phone = Phone;
        this.Name = Name;
        this.Pk=PK;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


    public String getPk() {
        return Pk;
    }

    public void setPk(String pk) {
        Pk = pk;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
