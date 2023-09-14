package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.controllers.ClientsController;
import com.sasd.appcotizacion.controllers.DBConnection;
import com.sasd.appcotizacion.controllers.ProductsController;
import com.sasd.appcotizacion.controllers.QuotesController;
import com.sasd.appcotizacion.models.ProductModel;
import com.sasd.appcotizacion.models.QuoteModel;
import com.sasd.appcotizacion.views.CommonsUIControls;
import com.sasd.appcotizacion.views.MainView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateQuote extends VBox {
    public CreateQuote(){
        setPadding(new Insets(15));
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

        searchField = new TextField();
        searchField.setPromptText("Escribe el nombre de un producto");
        searchField.setPrefWidth(250);
        Button searchButton = new Button("Buscar");
        HBox hBox = new HBox(searchField, searchButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(8);
        boxContent = new VBox(hBox);
        boxContent.setSpacing(10);

        TableColumn<ProductModel, Integer> idProd = new TableColumn<>("ID");
        TableColumn<ProductModel, String> nameProd = new TableColumn<>("Nombre");
        nameProd.setPrefWidth(150);

        TableColumn<ProductModel, String> variantProd = new TableColumn<>("Variante");
        variantProd.setPrefWidth(150);

        TableColumn<ProductModel, String> priceProd = new TableColumn<>("Precio Unidad");
        priceProd.setPrefWidth(150);

        TableColumn<ProductModel, Integer> numberOfProd = new TableColumn<>("Cantidad");
        numberOfProd.setPrefWidth(150);

        TableColumn<ProductModel, Double> total = new TableColumn<>("Total");
        total.setPrefWidth(150);

        idProd.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameProd.setCellValueFactory(new PropertyValueFactory<>("productName"));
        variantProd.setCellValueFactory(new PropertyValueFactory<>("productVariant"));
        priceProd.setCellValueFactory(new PropertyValueFactory<>("formattedPrice"));
        numberOfProd.setCellValueFactory(new PropertyValueFactory<>("numberOfProd"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));

        productsTable = new TableView<>();
        productsTable.getColumns().addAll(idProd, nameProd, variantProd, priceProd, numberOfProd, total);
        getChildren().add(productsTable);

        Label clientLabel = new Label("Cliente: ");
        selectedClient = new HBox(clientLabel);
        selectedClient.setPadding(new Insets(10, 0, 0, 0));
        selectedClient.setAlignment(Pos.BOTTOM_LEFT);

        Label totalLabel = new Label("Total: ");
        totalAmountBox = new HBox(totalLabel);
        totalAmountBox.setAlignment(Pos.BOTTOM_LEFT);
        totalAmountBox.setPadding(new Insets(15, 0, 0, 0));

        Label[] labels = {clientLabel, totalLabel};
        for(Label l : labels){
            l.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
            l.setTextFill(MainView.MAIN_COLOR);
        }

        getChildren().add(totalAmountBox);
        getChildren().add(selectedClient);

        searchButton.setOnMouseClicked(e -> {
            searchProducts();
        });

        searchField.setOnKeyTyped(e -> {
            //Prevent enter in dialog when the text field have the focus, i don't know how this work c:
        });
        searchField.setOnKeyPressed(e -> {
            switch (e.getCode().getName()){
                case "Esc" -> boxContent.requestFocus();
                case "Enter" -> searchProducts();
            }
        });

        addProductButton.setOnMouseClicked(e -> {
            Dialog<ButtonType> dialog = createDialog("Agregar Producto", boxContent);
            dialog.showAndWait().ifPresent(ev -> {
                searchField.clear();
                if (boxContent.getChildren().toArray().length == 2){
                    boxContent.getChildren().remove(1);
                }
            });
        });

        addClientButton.setOnMouseClicked(e -> {
            TextField searchField = new TextField();
            searchField.setPromptText("Buscar cliente");
            searchField.setOnKeyPressed(ev -> {
                switch (ev.getCode().getName()){
                    case "Esc" -> boxContent.requestFocus();
                    case "Enter" -> searchClient(searchField.getText());
                }
            });
            Button searchClientButton = new Button("Buscar");
            HBox searchClientBox = new HBox(searchField, searchClientButton);
            searchClientBox.setAlignment(Pos.CENTER);
            searchClientBox.setSpacing(10);
            searchBox = new VBox(searchClientBox);
            searchBox.setSpacing(15);
            dialogClient = createDialog("Agregar Cliente", searchBox);

            searchClientButton.setOnMouseClicked(ev -> {
                    searchClient(searchField.getText());
            });

            dialogClient.showAndWait();
        });

        removeProduct.disableProperty().bind(
                productsTable.getSelectionModel().selectedItemProperty().isNull()
        );
        removeProduct.setOnMouseClicked(e -> {
            productsTable.getItems().remove(productsTable.getSelectionModel().getSelectedIndex());
            calculateTotal();
            Text totalText = new Text(fmt.format(totalAmount));
            totalText.setFont(Font.font("Helvetica", 16));
            totalAmountBox.getChildren().remove(1);
            totalAmountBox.getChildren().add(totalText);
        });

        Button buttonCreateQuote = CommonsUIControls.createButton("Crear Cotizacion", 200, MainView.MAIN_FONT);
        HBox buttCreateBox = new HBox(buttonCreateQuote);
        buttCreateBox.setAlignment(Pos.CENTER);
        buttCreateBox.setPadding(new Insets(20));

        buttonCreateQuote.setOnMouseClicked(e -> {
            List<ProductModel> products = productsTable.getItems();
            if(products.isEmpty() && selectedClient.getChildren().toArray().length < 2){
                Alert alert = new Alert(Alert.AlertType.WARNING, "No has agregado ningun producto ni seleccionado un cliente", ButtonType.OK);
                alert.showAndWait();
            } else if (products.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No has agregado ningun producto", ButtonType.OK);
                alert.showAndWait();
            } else if (selectedClient.getChildren().toArray().length < 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No has seleccionado el cliente", ButtonType.OK);
                alert.showAndWait();
            } else {
                StringBuilder productsString = new StringBuilder();
                for(ProductModel p : products){
                    productsString.append(p).append(";");
                }
                String totalAmount = ((Text)totalAmountBox.getChildren().get(1)).getText();
                QuoteModel quote = new QuoteModel(productsString.toString(), totalAmount, Integer.parseInt(clientID));
                QuotesController.createQuote(quote);
                productsTable.getItems().clear();
                totalAmountBox.getChildren().remove(1);
                selectedClient.getChildren().remove(1);
            }
        });

        getChildren().add(buttCreateBox);
    }

    private void searchProducts(){
        if (boxContent.getChildren().toArray().length == 2){
            boxContent.getChildren().remove(1);
        }
        ResultSet rs = ProductsController.getProductsByName(searchField.getText().strip());
        try {
            VBox searchResults = new VBox();
            searchResults.setSpacing(10);
            while (rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String variant = rs.getString("variant");
                BigDecimal unitPrice = rs.getBigDecimal("unit_price");
                CheckBox check = new CheckBox(id);
                Text nameText = new Text(name);
                Text variantText = new Text(variant);
                TextField amount = new TextField();
                amount.setDisable(true);
                amount.setPrefWidth(70);
                amount.setPromptText("Cantidad");
                Button add = new Button("Agregar");
                add.setDisable(true);
                add.setOnMouseClicked(e -> {
                    BigDecimal amountNum = new BigDecimal(amount.getText());
                    ProductModel product = new ProductModel(Integer.parseInt(id),name, variant, fmt.format(unitPrice), amountNum.intValue(), fmt.format(unitPrice.multiply(amountNum)));
                    addProductButton(product);
                    check.setSelected(false);
                    check.setDisable(true);
                    amount.setDisable(true);
                    add.setDisable(true);
                });
                check.setOnAction(ev -> {
                    amount.setDisable(!check.isSelected());
                    add.setDisable(!check.isSelected());
                    if(check.isSelected()){
                        amount.setText("1");
                    }else {
                        amount.clear();
                    }
                });
                amount.setOnMouseClicked(event -> {
                    amount.selectAll();
                });
                amount.setOnKeyTyped(e -> {
                    for(int i = 0; i < amount.getText().length(); i++){
                        boolean isNumber = Character.isDigit(amount.getText().charAt(i));
                        add.setDisable(!isNumber);
                    }
                });
                HBox container = new HBox(check, nameText, variantText, amount, add);
                container.setSpacing(10);
                container.setAlignment(Pos.CENTER_LEFT);
                searchResults.getChildren().add(container);
            }
            boxContent.getChildren().add(searchResults);
            rs.close();
            ProductsController.closeConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void searchClient(String name){
        if(searchBox.getChildren().toArray().length == 2){
            searchBox.getChildren().remove(1);
        }

        ResultSet rs = ClientsController.selectClientByName(name);
        try {
            VBox container = new VBox();
            container.setAlignment(Pos.BOTTOM_LEFT);
            while (rs.next()){
                Text clientName = new Text(rs.getString("name"));
                Button add = new Button("Agregar");
                HBox hBox = new HBox(clientName, add);
                hBox.setSpacing(10);
                hBox.setAlignment(Pos.CENTER_LEFT);
                container.getChildren().add(hBox);
                String id = rs.getString("id");
                add.setOnAction(e -> {
                    clientID = id;
                    if(selectedClient.getChildren().toArray().length == 2){
                        selectedClient.getChildren().remove(1);
                        selectedClient.getChildren().add(clientName);
                    }else {
                        selectedClient.getChildren().add(clientName);
                    }
                    dialogClient.close();
                });
            }
            searchBox.getChildren().add(container);
            rs.close();
            ClientsController.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Dialog<ButtonType> createDialog(String title, VBox content){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.getDialogPane().setPrefSize(400, 400);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().setBackground(new Background(new BackgroundFill(MainView.SECONDARY_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Finalizar", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setOnShown(ev -> {
            searchField.requestFocus();
        });
        return dialog;
    }

    private void addProductButton(ProductModel product){
        productsTable.getItems().add(product);
        calculateTotal();
        Text totalText = new Text(fmt.format(totalAmount));
        totalText.setFont(Font.font("Helvetica", 16));
        if(totalAmountBox.getChildren().toArray().length == 2){
            totalAmountBox.getChildren().remove(1);
            totalAmountBox.getChildren().add(totalText);
        }else {
            totalAmountBox.getChildren().add(totalText);
        }
    }

    private void calculateTotal(){
        totalAmount = new BigDecimal(0);
        List<ProductModel> products = productsTable.getItems();
        for(ProductModel p : products){
            String total = p.getTotal();
            BigDecimal parseTotal = toBigDecimal(total);
            totalAmount = totalAmount.add(parseTotal);
        }
    }

    private BigDecimal toBigDecimal(String val){
        String cleanVal = val
                .replace("$", "")
                .replace(".", "")
                .replace(",", ".")
                .replaceAll("[^\\d.-]", "");
        return new BigDecimal(cleanVal);
    }

    private final VBox boxContent;
    private final TextField searchField;
    private final TableView<ProductModel> productsTable;
    private final HBox totalAmountBox;
    private BigDecimal totalAmount;
    private VBox searchBox;
    private final HBox selectedClient;
    private Dialog<ButtonType> dialogClient;
    private String clientID;
    private final NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
}
