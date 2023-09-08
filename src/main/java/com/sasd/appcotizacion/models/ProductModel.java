package com.sasd.appcotizacion.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ProductModel {
    private StringProperty productName;
    private StringProperty productVariant;
    private IntegerProperty numberOfProducts;
    private DoubleProperty productPrice;

    public ProductModel(String productName, String productVariant, int numberOfProducts, double price){
        setProductName(productName);
        setProductVariant(productVariant);
        setNumberOfProducts(numberOfProducts);
        setProductPrice(price);
    }

    public void setProductName(String name){
        productName.set(name);
    }
    public String getProductName(){
        return productName.get();
    }

    public void setProductVariant(String variant){
            productVariant.set(variant);
    }
    public String getProductVariant(){
        return productVariant.get();
    }

    public void setNumberOfProducts(int number){
        numberOfProducts.set(number);
    }
    public int getNumberOfProducts(){
        return numberOfProducts.get();
    }

    public void setProductPrice(double price){
        productPrice.set(price);
    }
    public double getProductPrice(){
        return productPrice.get();
    }

}
