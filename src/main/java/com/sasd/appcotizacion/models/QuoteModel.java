package com.sasd.appcotizacion.models;

public class QuoteModel {
    private final String products;
    private final String total;
    private final int clientID;

    public QuoteModel(String products, String total, int clientID){
        this.products = products;
        this.total = total;
        this.clientID = clientID;
    }

    public String getProducts() {
        return products;
    }

    public String getTotal() {
        return total;
    }

    public int getClientID() {
        return clientID;
    }
}










