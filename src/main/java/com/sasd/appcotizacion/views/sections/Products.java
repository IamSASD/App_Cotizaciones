package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.models.ProductModel;
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

        fieldsBox = new VBox(nameBox,variantBox, numberOfProdBox, uniPriceBox);
        fieldsBox.setSpacing(10);



        createProduct.setOnMouseClicked(e -> {
            dialog = CommonsUIControls.createDialog("Crear Producto", "Nuevo Producto", fieldsBox);
            createProductButton = (Button) dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(1));
            validateDialogFields(createProductButton);
            dialog.showAndWait().ifPresent(resp -> {
                if(resp.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)){
                    getDialogData();
                }
            });
        });

    }

    private void getDialogData(){
        String nameValue = nameField.getText();
        String variantValue = variantField.getText();
        int numberOfProdValue = Integer.parseInt(numberOfProdField.getText());
        double unitPriceValue = Double.parseDouble(unitPriceField.getText());

        ProductModel newProduct = new ProductModel(nameValue, variantValue, numberOfProdValue, unitPriceValue);

        System.out.println(nameValue + " " + variantValue + " " + numberOfProdValue + " " + unitPriceValue);

    }

    private void isNumber(Button butt){
        TextField[] fields = {numberOfProdField, unitPriceField};
        for (TextField f : fields){
            f.setOnKeyTyped(e -> {

            });
        }
    }

    private void validateDialogFields(Button butt){

        TextField[] fields = {nameField, variantField, numberOfProdField, unitPriceField};

        for(TextField f : fields){
            f.setOnKeyTyped(e -> {
                butt.setDisable(nameField.getText().isEmpty() || variantField.getText().isEmpty() || numberOfProdField.getText().isEmpty() || unitPriceField.getText().isEmpty());
                if(f == numberOfProdField || f == unitPriceField){
                    for (int i = 0; i < f.getText().length(); i++){
                        boolean isNum = Character.isDigit(f.getText().charAt(i));
                        if(!isNum) {
                            if(fieldsBox.getChildren().get(0).getTypeSelector().equals("Text")){
                                fieldsBox.getChildren().remove(0);
                            }
                            Text errorMsg = new Text("Este campo no puede contener letras");
                            fieldsBox.getChildren().add(0, errorMsg);
                            System.out.println();
                            butt.setDisable(true);
                            break;
                        };
                        if(fieldsBox.getChildren().get(0).getTypeSelector().equals("Text")){
                            fieldsBox.getChildren().remove(0);
                        }
                    }
                }
            });
        }

    }

    private TableView<ProductModel> productTable;
    private final VBox fieldsBox;
    private Dialog<ButtonType> dialog;
    private final HBox  nameBox = CommonsUIControls.createInputField("Nombre");
    private final HBox variantBox = CommonsUIControls.createInputField("Variant");
    private final HBox numberOfProdBox = CommonsUIControls.createInputField("Cantidad");
    private final HBox uniPriceBox = CommonsUIControls.createInputField("Precio unidad");
    private Button createProductButton;
    private final TextField nameField = ((TextField) nameBox.getChildren().get(1));
    private final TextField variantField = ((TextField) variantBox.getChildren().get(1));
    private final TextField numberOfProdField = ((TextField) numberOfProdBox.getChildren().get(1));
    private final TextField unitPriceField = ((TextField) uniPriceBox.getChildren().get(1));

}
