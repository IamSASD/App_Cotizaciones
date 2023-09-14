package com.sasd.appcotizacion.views;

import com.sasd.appcotizacion.views.sections.Clients;
import com.sasd.appcotizacion.views.sections.CreateQuote;
import com.sasd.appcotizacion.views.sections.Products;
import com.sasd.appcotizacion.views.sections.Quotes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainView extends Application {
    public static final Font MAIN_FONT = Font.font("Helvetica", FontWeight.BOLD, 20);
    public static final Font SECONDARY_FONT = Font.font("Helvetica", FontWeight.BOLD, 17);
    public static final Paint MAIN_COLOR = Color.rgb(255, 155, 80);
    public static final Paint SECONDARY_COLOR = Color.rgb(252, 252, 252);
    public static final Paint THIRD_COLOR = Color.rgb(240, 240, 240);
    public static CreateQuote create_quotation_section;
    public static Products products_section;
    public static Clients clients_section;
    public static Quotes quotes_section;
    @Override
    public void start(Stage stage) throws Exception {
        create_quotation_section = new CreateQuote();
        products_section = new Products();
        clients_section = new Clients();
        quotes_section = new Quotes();

        SectionContainerBox sectionContainer = new SectionContainerBox(create_quotation_section);

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
