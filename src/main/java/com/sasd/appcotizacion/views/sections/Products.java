package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.views.CommonsUIControls;
import com.sasd.appcotizacion.views.MainView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

import java.util.ArrayList;

public class Products extends VBox {

    public Products(){
        HBox title = CommonsUIControls.createTitle("Productos");
        getChildren().add(title);

        Button createProduct = CommonsUIControls.createButton("Crear Producto", 200, MainView.SECONDARY_FONT);
        HBox buttonContainer = new HBox(createProduct);
        buttonContainer.setPadding(new Insets(10));
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        getChildren().add(buttonContainer);

        HBox nameBox = CommonsUIControls.createInputField("Nombre");
        HBox variantBox = CommonsUIControls.createInputField("Variant");
        HBox numberOfProdBox = CommonsUIControls.createInputField("Cantidad");
        HBox uniPriceBox = CommonsUIControls.createInputField("Precio unidad");

        VBox fieldsBox = new VBox(nameBox,variantBox, numberOfProdBox, uniPriceBox);
        fieldsBox.setSpacing(10);

        createProduct.setOnMouseClicked(e -> {
            Dialog<VBox> dialog = CommonsUIControls.createDialog("Crear Producto", "Nuevo Producto", fieldsBox);
            dialog.showAndWait();
        });

    }

}
