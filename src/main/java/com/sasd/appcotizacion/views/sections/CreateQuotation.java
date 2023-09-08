package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.views.CommonsUIControls;
import com.sasd.appcotizacion.views.MainView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CreateQuotation extends VBox {
    public CreateQuotation(){

        HBox title = CommonsUIControls.createTitle("Nueva Cotizacion");
        getChildren().add(title);

        Button button = CommonsUIControls.createButton("Agregar Producto", 200, MainView.SECONDARY_FONT);
        HBox addbuttonContainer = new HBox(button);
        addbuttonContainer.setPadding(new Insets(10));
        addbuttonContainer.setAlignment(Pos.CENTER_RIGHT);

        TextField textField = new TextField();

        getChildren().add(addbuttonContainer);
        getChildren().add(textField);

    }

}
