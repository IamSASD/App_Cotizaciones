package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.controllers.ProductsController;
import com.sasd.appcotizacion.models.ProductModel;
import com.sasd.appcotizacion.views.CommonsUIControls;
import com.sasd.appcotizacion.views.MainView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Products extends VBox {
    public Products(){

        setPadding(new Insets(15));

        HBox title = CommonsUIControls.createTitle("Productos");
        getChildren().add(title);

        Button createProduct = CommonsUIControls.createButton("Crear Producto", 200, MainView.SECONDARY_FONT);
        Button editButton = CommonsUIControls.createButton("Edita Producto", 180, MainView.SECONDARY_FONT);
        Button deleteButton = CommonsUIControls.createButton("Eliminar Producto", 180, MainView.SECONDARY_FONT);

        HBox buttonContainer = new HBox(deleteButton, editButton, createProduct);
        buttonContainer.setPadding(new Insets(10));
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.setSpacing(8);
        getChildren().add(buttonContainer);

        fieldsBox = new VBox(nameBox,variantBox, uniPriceBox);
        fieldsBox.setSpacing(10);

        TableColumn<ProductModel, Integer> idProd = new TableColumn<>("ID");
        TableColumn<ProductModel, String> nameProd = new TableColumn<>("Nombre");
        nameProd.setPrefWidth(150);

        TableColumn<ProductModel, String> variantProd = new TableColumn<>("Variante");
        variantProd.setPrefWidth(150);

        TableColumn<ProductModel, Double> priceProd = new TableColumn<>("Precio Unidad");
        priceProd.setPrefWidth(150);

        idProd.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameProd.setCellValueFactory(new PropertyValueFactory<>("productName"));
        variantProd.setCellValueFactory(new PropertyValueFactory<>("productVariant"));
        priceProd.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        productTable = new TableView<>();

        productTable.getColumns().addAll(idProd, nameProd, variantProd, priceProd);

        getChildren().add(productTable);
        loadDataTable();

        createProduct.setOnMouseClicked(e -> {
            dialog = CommonsUIControls.createDialog("Crear Producto", "Nuevo Producto", fieldsBox, true);
            createProductButton = (Button) dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(1));
            validateDialogFields(createProductButton);
            dialog.showAndWait().ifPresent(resp -> {
                if(resp.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)){
                    getDialogData();
                }else {
                    cleanDialog();
                }
            });
        });

        editButton.disableProperty().bind(
                productTable.getSelectionModel().selectedItemProperty().isNull()
        );
        deleteButton.disableProperty().bind(
                productTable.getSelectionModel().selectedItemProperty().isNull()
        );

        editButton.setOnMouseClicked(e -> {
            ProductModel dataToEdit = productTable.getSelectionModel().selectedItemProperty().getValue();
            int id = dataToEdit.getId();
            nameField.setText(dataToEdit.getProductName());
            variantField.setText(dataToEdit.getProductVariant());
            unitPriceField.setText(String.valueOf(Math.round(dataToEdit.getProductPrice())));
            Dialog<ButtonType> editDialog = CommonsUIControls.createDialog("Editar", "Editar Producto", fieldsBox, true);
            Button editProdButton = (Button) editDialog.getDialogPane().lookupButton(editDialog.getDialogPane().getButtonTypes().get(1));
            validateDialogFields(editProdButton);
            editDialog.showAndWait().ifPresent(resp -> {
                if(resp.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)){
                    updateProductTable(id);
                }else {
                    cleanDialog();
                }
            });
        });

        deleteButton.setOnMouseClicked(e -> {
            int prodId = productTable.getSelectionModel().selectedItemProperty().getValue().getId();
            Text deleteMsg = new Text("Estas seguro de querer eleminar este producto?");
            VBox msgBox = new VBox(deleteMsg);
            Dialog<ButtonType> deleteDialog = CommonsUIControls.createDialog("Si", "Eliminar Producto", msgBox, false);
            deleteDialog.showAndWait().ifPresent(resp -> {
                if(resp.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)){
                    deleteProductTable(prodId);
                }
            });
        });

    }

    private void deleteProductTable(int id){
        ProductsController.deleteProduct(id);
        productTable.getItems().clear();
        loadDataTable();
    }

    private void updateProductTable(int id){
        String nameValue = nameField.getText();
        String variantValue = variantField.getText();
        double unitPriceValue = Double.parseDouble(unitPriceField.getText());

        ProductModel editedProd = new ProductModel(id, nameValue, variantValue, unitPriceValue);
        ProductsController.updateProduct(editedProd);
        productTable.getItems().clear();
        loadDataTable();
        cleanDialog();
    }

    private void getDialogData(){
        String nameValue = nameField.getText();
        String variantValue = variantField.getText();
        double unitPriceValue = Double.parseDouble(unitPriceField.getText());

        ProductModel newProduct = new ProductModel(nameValue, variantValue, unitPriceValue);

        ProductsController.setProducts(newProduct);
        productTable.getItems().clear();
        loadDataTable();
        cleanDialog();

    }

    private void loadDataTable(){
        ResultSet rs = ProductsController.getAllProducts();
        try {
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String variant = rs.getString("variant");
                double price = rs.getDouble("unit_price");
                ProductModel product = new ProductModel(id, name, variant, price);
                productTable.getItems().add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateDialogFields(Button butt){

        TextField[] fields = {nameField, variantField, unitPriceField};

        for(TextField f : fields){
            f.setOnKeyTyped(e -> {
                butt.setDisable(nameField.getText().isEmpty() || variantField.getText().isEmpty() || unitPriceField.getText().isEmpty());
                if(f == unitPriceField){
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

    public void cleanDialog(){
        nameField.clear();
        variantField.clear();
        unitPriceField.setText("");
    }

    private TableView<ProductModel> productTable;
    private final VBox fieldsBox;
    private Dialog<ButtonType> dialog;
    private final HBox  nameBox = CommonsUIControls.createInputField("Nombre");
    private final HBox variantBox = CommonsUIControls.createInputField("Variant");
    private final HBox uniPriceBox = CommonsUIControls.createInputField("Precio unidad");
    private Button createProductButton;
    private final TextField nameField = ((TextField) nameBox.getChildren().get(1));
    private final TextField variantField = ((TextField) variantBox.getChildren().get(1));
    private final TextField unitPriceField = ((TextField) uniPriceBox.getChildren().get(1));

}
