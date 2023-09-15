package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.controllers.GeneratePDF;
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

        parentBox = new VBox();
        parentBox.setBackground(background);
        parentBox.setSpacing(20);
        ScrollPane scrollPane = new ScrollPane(parentBox);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setFitToWidth(true);
        scrollPane.setBackground(new Background(new BackgroundFill(MainView.SECONDARY_COLOR, new CornerRadii(13), Insets.EMPTY)));

        loadQuotes();
        quotesBoxIsEmpty();
        getChildren().add(scrollPane);

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
                String phone = rs.getString("client_phone");
                HBox item = createQuoteItem(id, clientName, date, products, total, clientID, phone);
                parentBox.getChildren().add(item);
            }
            rs.close();
            QuotesController.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static HBox createQuoteItem(String id, String clientName, String date, String products, String total, String clientID, String phone){

        HBox idBox = infoBox("ID: ", id);
        HBox clientBox = infoBox("Cliente", clientName);
        HBox dateBox = infoBox("Fecha: ", date);

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

        deleteQuoteButt.setOnMouseClicked(e -> {
            QuotesController.removeQuote(Integer.parseInt(id));
            loadQuotes();
            quotesBoxIsEmpty();
        });

        generatePDFButt.setOnMouseClicked(e -> {
            GeneratePDF.createPDF(id, products, clientName, date, clientID, phone, total);
        });

        return item;
    }

    private static void quotesBoxIsEmpty(){
        if(parentBox.getChildren().toArray().length == 0){
            Text text = new Text("No hay cotizaciones para mostrar");
            text.setFont(MainView.MAIN_FONT);
            text.setFill(MainView.MAIN_COLOR);
            parentBox.getChildren().add(text);
        }else {
            if(parentBox.getChildren().get(0) instanceof Text){
                parentBox.getChildren().remove(0);
            }
        }
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

        VBox[] vBoxes = {nameBox, variantBox, unitPrice, numberOfProd, totalAmount};
        for(VBox b : vBoxes){
            b.setSpacing(10);
        }

        HBox columnsContainer = new HBox(nameBox, variantBox, unitPrice, numberOfProd, totalAmount);
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

        HBox totalBox = infoBox("Total: ", total);
        totalBox.setBackground(backgroundRounded);
        totalBox.setPadding(new Insets(10));
        totalBox.setAlignment(Pos.CENTER_LEFT);

        VBox contentBox = new VBox(quoteID, quoteDate, sep, clientIDBox, clientNameBox, scrollPane, totalBox);
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
    private static final Background backgroundRounded = new Background(new BackgroundFill(Color.rgb(248,248,248), new CornerRadii(12), Insets.EMPTY));

}












