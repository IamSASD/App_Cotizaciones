package com.sasd.appcotizacion.controllers;

import com.sasd.appcotizacion.models.QuoteModel;

import java.sql.*;

public class QuotesController {
    private static Connection conn;
    public static void createQuote(QuoteModel quote){
        try{
            conn = DBConnection.getConnection();

            PreparedStatement statement = conn.prepareStatement("INSERT INTO quotes(products, total, clientId) VALUES(?,?,?)");
            statement.setString(1, quote.getProducts());
            statement.setString(2, quote.getTotal());
            statement.setInt(3, quote.getClientID());
            statement.execute();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getAllQuotes(){
        ResultSet rs;
        try{
            conn = DBConnection.getConnection();

            rs = conn.createStatement().executeQuery("SELECT * FROM quotes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}






