package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.controllers.QuotesController;
import com.sasd.appcotizacion.models.QuoteModel;
import com.sasd.appcotizacion.views.CommonsUIControls;
import com.sasd.appcotizacion.views.MainView;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Quotes extends VBox {

    public Quotes(){
        setPadding(new Insets(10));
        HBox title = CommonsUIControls.createTitle("Cotizaciones");
        getChildren().add(title);

        Button refreshButt = CommonsUIControls.createButton("Actualizar", 150, MainView.SECONDARY_FONT);
        HBox buttonRefreshBox = new HBox(refreshButt);
        buttonRefreshBox.setAlignment(Pos.CENTER_RIGHT);
        buttonRefreshBox.setPadding(new Insets(0, 0, 13, 0));
        getChildren().add(buttonRefreshBox);

        parentBox = new VBox();
        parentBox.setBackground(background);
        parentBox.setSpacing(20);
        ScrollPane scrollPane = new ScrollPane(parentBox);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setFitToWidth(true);
        scrollPane.setBackground(new Background(new BackgroundFill(MainView.SECONDARY_COLOR, new CornerRadii(13), Insets.EMPTY)));

        loadQuotes();

        getChildren().add(scrollPane);

        refreshButt.setOnMouseClicked(e -> {
            loadQuotes();
        });

    }

    public static void loadQuotes(){
        parentBox.getChildren().removeAll(parentBox.getChildren());
        ResultSet rs = QuotesController.getAllQuotes();
        try {
            while (rs.next()){
                String id = rs.getString("id");
                String products = rs.getString("products");
                String total = rs.getString("total");
                String date = rs.getString("date");
                String clientName = rs.getString("client_name");
                String clientID = rs.getString("client_id");
                HBox item = createQuoteItem(id, clientName, date, products, total, clientID);
                parentBox.getChildren().add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static HBox createQuoteItem(String id, String clientName, String date, String products, String total, String clientID){
        Font font = Font.font("Helvetica", 15);
        Label idLabel = new Label("ID: ");
        idLabel.setFont(font);
        Text idText = new Text(id);
        idText.setFont(font);
        HBox idBox = new HBox(idLabel, idText);
        idBox.setAlignment(Pos.CENTER_RIGHT);

        Label clientLabel = new Label("Cliente: ");
        clientLabel.setFont(font);
        Text clientText = new Text(clientName);
        clientText.setFont(font);
        HBox clientBox = new HBox(clientLabel, clientText);
        clientBox.setAlignment(Pos.CENTER_RIGHT);

        Label dateLabel = new Label("Fecha: ");
        dateLabel.setFont(font);
        Text dateText = new Text(date);
        dateText.setFont(font);
        HBox dateBox = new HBox(dateLabel, dateText);
        dateBox.setAlignment(Pos.CENTER_RIGHT);

        Button generatePDFButt = CommonsUIControls.createButton("Generar PDF", 170, MainView.SECONDARY_FONT);
        Button deleteQuoteButt = CommonsUIControls.createButton("Eliminar", 170, MainView.SECONDARY_FONT);
        HBox buttonsActions = new HBox(generatePDFButt, deleteQuoteButt);
        buttonsActions.setSpacing(6);
        buttonsActions.setPadding(new Insets(0, 0, 0, 10));

        HBox item = new HBox(idBox, clientBox, dateBox, buttonsActions);
        item.setAlignment(Pos.CENTER);
        item.setSpacing(10);
        item.setPadding(new Insets(15));
        item.setBackground(backgroundRounded);
        item.setCursor(Cursor.HAND);

        item.setOnMouseClicked(e -> {
            if(e.getClickCount() >= 2){
                quoteDialog(id, clientName, date, products, total, clientID);
            }
        });

        return item;
    }

    private static void quoteDialog(String id, String clientName, String date, String products, String total, String clientID){
        Dialog<ButtonType> dialog = new Dialog<>();
        DialogPane pane = dialog.getDialogPane();

        HBox quoteID = infoBox("ID: ", id);
        HBox quoteDate = infoBox("Fecha: ", date);
        Separator sep = new Separator(Orientation.HORIZONTAL);
        HBox clientIDBox =infoBox("ID Cliente: ", clientID);
        HBox clientNameBox = infoBox("Cliente: ", clientName);

        pane.setPrefSize(500, 500);
        pane.setBackground(background);
        pane.getButtonTypes().add(ButtonType.FINISH);

        Label idCol = new Label("ID");
        VBox idBox = new VBox(idCol);
        Label nameCol = new Label("Nombre");
        VBox nameBox = new VBox(nameCol);
        Label variantCol = new Label("Variante");
        VBox variantBox = new VBox(variantCol);
        Label unitPriceCol = new Label("Precio Unidad");
        VBox unitPrice = new VBox(unitPriceCol);
        Label numberOfProdCol = new Label("Cantidad");
        VBox numberOfProd = new VBox(numberOfProdCol);
        Label totalCol = new Label("Total");
        VBox totalAmount = new VBox(totalCol);

        HBox columnsContainer = new HBox(idBox, nameBox, variantBox, unitPrice, numberOfProd, totalAmount);
        columnsContainer.setSpacing(10);
        columnsContainer.setPadding(new Insets(10));

        String[] productsSplit = products.split(";");
        for(String p : productsSplit){
            int counter = 0;
            for (String j : p.split(" ")){
                Text text = new Text(j);
                ((VBox) columnsContainer.getChildren().get(counter)).getChildren().add(text);
                counter++;
            }
        }

        ScrollPane scrollPane = new ScrollPane(columnsContainer);
        scrollPane.setBackground(backgroundRounded);
        scrollPane.setFitToWidth(true);

        VBox contentBox = new VBox(quoteID, quoteDate, sep, clientIDBox, clientNameBox, scrollPane);
        contentBox.setSpacing(10);
        pane.setContent(contentBox);

        dialog.showAndWait();

    }

    private static HBox infoBox(String labelText, String texContent){
        Font font = Font.font("Helvetica", 15);
        Label label = new Label(labelText);
        label.setFont(font);
        Text text = new Text(texContent);
        text.setFont(font);
        HBox hbox = new HBox(label, text);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        return hbox;
    }

    private static VBox parentBox;
    private static final Background background = new Background(new BackgroundFill(MainView.SECONDARY_COLOR, CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background backgroundRounded = new Background(new BackgroundFill(MainView.SECONDARY_COLOR, new CornerRadii(12), Insets.EMPTY));

}












