module com.sasd.appcotizacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.github.librepdf.openpdf;

    exports com.sasd.appcotizacion.views;
    exports com.sasd.appcotizacion.models;
    exports com.sasd.appcotizacion.controllers;
}