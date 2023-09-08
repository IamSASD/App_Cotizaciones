package com.sasd.appcotizacion.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ProductModel {
    private String productName;
    private String productVariant;
    private int numberOfProducts;
    private double productPrice;

    public ProductModel(String productName, String productVariant, int numberOfProducts, double price){
        this.productName = productName;
        this.productVariant = productVariant;
        this.numberOfProducts = numberOfProducts;
        this.productPrice = price;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductVariant() {
        return productVariant;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public double getProductPrice() {
        return productPrice;
    }
}
