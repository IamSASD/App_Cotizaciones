package com.sasd.appcotizacion.controllers;

import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:Cotizaciones.db");

        Statement statement = conn.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS products(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(40), variant VARCHAR(30), unit_price REAL)");

        return conn;
    }
}














