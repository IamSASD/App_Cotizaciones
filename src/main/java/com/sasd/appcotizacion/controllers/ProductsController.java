package com.sasd.appcotizacion.controllers;

import com.sasd.appcotizacion.models.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsController {

    public static void setProducts(ProductModel product){
        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement statement = conn.prepareStatement("INSERT INTO products(name, variant, unit_price) VALUES(?, ?, ?)");
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductVariant());
            statement.setDouble(3, product.getProductPrice());
            statement.execute();

            conn.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getAllProducts(){
        ResultSet rs;
        try {
            Connection conn = DBConnection.getConnection();
            rs = conn.createStatement().executeQuery("SELECT * FROM products");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
