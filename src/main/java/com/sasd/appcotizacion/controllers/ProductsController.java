package com.sasd.appcotizacion.controllers;

import com.sasd.appcotizacion.models.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsController {
    private static Connection conn;
    public static void setProducts(ProductModel product){
        try {
            conn = DBConnection.getConnection();

            PreparedStatement statement = conn.prepareStatement("INSERT INTO products(name, variant, unit_price) VALUES(?, ?, ?)");
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductVariant());
            statement.setDouble(3, product.getProductPrice());
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getAllProducts(){
        ResultSet rs;
        try {
            conn = DBConnection.getConnection();
            rs = conn.createStatement().executeQuery("SELECT * FROM products");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static void updateProduct(ProductModel product){
        try {
            conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE products SET name = ?, variant = ?, unit_price = ? WHERE id = ?");
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductVariant());
            statement.setDouble(3, product.getProductPrice());
            statement.setInt(4, product.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteProduct(int id){
        try {
            conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM products WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
