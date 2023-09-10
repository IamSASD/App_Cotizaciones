package com.sasd.appcotizacion.controllers;

import com.sasd.appcotizacion.models.ClientModel;
import com.sasd.appcotizacion.models.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientsController {
    private static Connection conn;

    public static void createClient(ClientModel client){
        try {
            conn = DBConnection.getConnection();

            PreparedStatement statement = conn.prepareStatement("INSERT INTO clients(name, phone) VALUES(?, ?)");
            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.execute();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getAllClients(){
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            rs = conn.createStatement().executeQuery("SELECT * FROM clients");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void updateClient(ClientModel client){
        try {
            conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE clients SET name = ?, phone = ? WHERE id = ?");
            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.setInt(3, client.getId());
            statement.execute();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteClient(int id){
        try {
            conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM clients WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
