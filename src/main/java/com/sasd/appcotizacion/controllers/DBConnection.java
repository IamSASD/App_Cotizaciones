package com.sasd.appcotizacion.controllers;

import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:Cotizaciones.db");

        Statement statement = conn.createStatement();

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS products(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(40), variant VARCHAR(30), unit_price REAL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS clients(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100), phone VARCHAR(15))");

        statement.close();
        return conn;
    }
}














