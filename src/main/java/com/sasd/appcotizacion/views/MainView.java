package com.sasd.appcotizacion.views;

import com.sasd.appcotizacion.views.sections.Clients;
import com.sasd.appcotizacion.views.sections.CreateQuotation;
import com.sasd.appcotizacion.views.sections.Products;
import com.sasd.appcotizacion.views.sections.Quotations;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.atomic.AtomicReference;

public class MainView extends Application {
    public static final Font MAIN_FONT = Font.font("Helvetica", FontWeight.BOLD, 20);
    public static final Font SECONDARY_FONT = Font.font("Helvetica", FontWeight.BOLD, 17);
    public static final Paint MAIN_COLOR = Color.rgb(255, 155, 80);
    public static final Paint SECONDARY_COLOR = Color.rgb(252, 252, 252);
    public static final Paint THIRD_COLOR = Color.rgb(240, 240, 240);
    public static final CreateQuotation CREATE_QUOTATION_SECTION = new CreateQuotation();
    public static final Products PRODUCTS_SECTION = new Products();
    public static final Clients CLIENTS_SECTION = new Clients();
    public static final Quotations QUOTATIONS_SECTION = new Quotations();
    @Override
    public void start(Stage stage) throws Exception {

        SectionContainerBox sectionContainer = new SectionContainerBox(CREATE_QUOTATION_SECTION);

        HBox mainBox = new HBox(new MenuBox(sectionContainer), sectionContainer);

        Scene scene = new Scene(mainBox, 1000, 800);

        stage.setTitle("Cotizaciones");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
