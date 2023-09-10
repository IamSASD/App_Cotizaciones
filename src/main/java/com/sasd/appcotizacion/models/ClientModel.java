package com.sasd.appcotizacion.models;

public class ClientModel {
    private int id;
    private String name;
    private String phone;
    public ClientModel(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public ClientModel(int id, String name, String phone){
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
