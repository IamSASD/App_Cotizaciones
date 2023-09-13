package com.sasd.appcotizacion.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class ProductModel {
    private int id;
    private String productName;
    private String productVariant;
    private BigDecimal productPrice;
    private int numberOfProd;
    private String total;

    public ProductModel(String productName, String productVariant, BigDecimal price){
        this.productName = productName;
        this.productVariant = productVariant;
        this.productPrice = price;
    }

    public ProductModel(int id, String productName, String productVariant, BigDecimal price){
        this.id = id;
        this.productName = productName;
        this.productVariant = productVariant;
        this.productPrice = price;
    }

    public ProductModel(int id, String productName, String productVariant, BigDecimal price, int numberOfProd,  String total){
        this.id = id;
        this.productName = productName;
        this.productVariant = productVariant;
        this.productPrice = price;
        this.numberOfProd = numberOfProd;
        this.total = total;
    }

    public int getId(){
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductVariant() {
        return productVariant;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public int getNumberOfProd(){
        return numberOfProd;
    }

    public String getTotal(){
        return total;
    }
}
