package com.sasd.appcotizacion.controllers;

import java.io.File;
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        File dir = new File("AppCotizaciones/");
        dir.mkdir();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:AppCotizaciones/Cotizaciones.db");

        Statement statement = conn.createStatement();

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS products(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, variant TEXT, unit_price REAL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS clients(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS quotes(id INTEGER PRIMARY KEY AUTOINCREMENT, products TEXT, total TEXT, date TEXT, clientId INTEGER)");

        statement.close();
        return conn;
    }
}














