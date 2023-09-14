package com.sasd.appcotizacion.controllers;

import com.sasd.appcotizacion.models.QuoteModel;

import java.sql.*;

public class QuotesController {
    private static Connection conn;
    public static void createQuote(QuoteModel quote){
        try{
            conn = DBConnection.getConnection();

            PreparedStatement statement = conn.prepareStatement("INSERT INTO quotes(products, total, date,clientId) VALUES(?,?, datetime('now', 'localtime'),?)");
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

            rs = conn.createStatement().executeQuery("SELECT q.id, q.products, q.total, q.date, c.name client_name, c.id client_id FROM quotes q INNER JOIN clients c ON c.id = q.clientId");
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






