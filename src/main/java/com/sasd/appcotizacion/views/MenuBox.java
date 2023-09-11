package com.sasd.appcotizacion.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MenuBox extends VBox {
    private final double BUTTON_WITH = 200;
    private final double BOX_WITH = 300;
    private SectionContainerBox section;
    public MenuBox(SectionContainerBox section){
        this.section = section;

        setBackground(new Background(new BackgroundFill(MainView.SECONDARY_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.rgb(248,248,248), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 4, 0, 0))));
        setPrefWidth(BOX_WITH);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20, 0, 0, 0));
        setSpacing(15);

        Button createQuotation = CommonsUIControls.createButton("Crear Cotizacion", BUTTON_WITH, MainView.MAIN_FONT);
        createQuotation.setOnMouseClicked(e -> section.getChildren().setAll(MainView.CREATE_QUOTATION_SECTION));

        Button products = CommonsUIControls.createButton("Productos", BUTTON_WITH, MainView.MAIN_FONT);
        products.setOnMouseClicked(e -> section.getChildren().setAll(MainView.PRODUCTS_SECTION));

        Button clients = CommonsUIControls.createButton("Clientes", BUTTON_WITH, MainView.MAIN_FONT);
        clients.setOnMouseClicked(e -> section.getChildren().setAll(MainView.CLIENTS_SECTION));

        Button quotations = CommonsUIControls.createButton("Cotizaciones", BUTTON_WITH, MainView.MAIN_FONT);
        quotations.setOnMouseClicked(e -> section.getChildren().setAll(MainView.QUOTATIONS_SECTION));

        getChildren().addAll(createQuotation, products, clients, quotations);

    }

}
