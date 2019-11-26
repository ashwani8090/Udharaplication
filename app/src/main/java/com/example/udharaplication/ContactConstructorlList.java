package com.example.udharaplication;

public class ContactConstructorlList {

    private String Phone,Name;
    private String Pk;
    private String uri;

    public ContactConstructorlList() {
    }

    public ContactConstructorlList(String PK,String Phone,  String Name,String uri) {
        this.Phone = Phone;
        this.Name = Name;
        this.Pk=PK;
        this.uri=uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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
