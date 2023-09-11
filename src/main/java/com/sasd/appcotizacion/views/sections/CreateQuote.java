package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.controllers.ProductsController;
import com.sasd.appcotizacion.views.CommonsUIControls;
import com.sasd.appcotizacion.views.MainView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateQuote extends VBox {
    private Button doneButton;
    public CreateQuote(){

        HBox title = CommonsUIControls.createTitle("Nueva Cotizacion");
        getChildren().add(title);

        Button addProductButton = CommonsUIControls.createButton("Agregar Producto", 200, MainView.SECONDARY_FONT);
        Button addClientButton = CommonsUIControls.createButton("Agregar Cliente", 180, MainView.SECONDARY_FONT);
        Button removeProduct = CommonsUIControls.createButton("Eliminar Producto", 180, MainView.SECONDARY_FONT);

        HBox addbuttonContainer = new HBox(removeProduct, addClientButton, addProductButton);
        addbuttonContainer.setSpacing(10);
        addbuttonContainer.setPadding(new Insets(10));
        addbuttonContainer.setAlignment(Pos.CENTER_RIGHT);

        getChildren().add(addbuttonContainer);

        TextField searchField = new TextField();
        searchField.setPromptText("Escribe el nombre de un producto");
        searchField.setPrefWidth(250);
        Button searchButton = new Button("Buscar");
        HBox hBox = new HBox(searchField, searchButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(8);
        VBox boxContent = new VBox(hBox);
        boxContent.setSpacing(10);

        searchField.setOnKeyTyped(e -> {
            doneButton.setDisable(searchField.getText().isEmpty());
        });

        searchButton.setOnMouseClicked(e -> {
            ResultSet rs = ProductsController.getProductNameAndVariant(searchField.getText());
            try {
                VBox searchResults = new VBox();
                searchResults.setSpacing(10);
                while (rs.next()){
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String variant = rs.getString("variant");
                    CheckBox check = new CheckBox(id);
                    Text nameText = new Text(name);
                    Text variantText = new Text(variant);
                    TextField amount = new TextField();
                    amount.setDisable(true);
                    amount.setPrefWidth(70);
                    check.setOnAction(ev -> {
                        amount.setDisable(!check.isSelected());
                        amount.setText("1");
                    });
                    amount.setPromptText("Cantidad");
                    HBox container = new HBox(check, nameText, variantText, amount);
                    container.setSpacing(10);
                    searchResults.getChildren().add(container);
                }
                boxContent.getChildren().add(searchResults);
                rs.close();
                ProductsController.closeConnection();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        addProductButton.setOnMouseClicked(e -> {
            Dialog<ButtonType> dialog = CommonsUIControls.createDialog("Agregar", "Agregar nuevo producto", boxContent, true);
            doneButton = (Button) dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(1));
            dialog.showAndWait();
        });

    }

}
