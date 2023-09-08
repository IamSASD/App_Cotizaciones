package com.sasd.appcotizacion.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CommonsUIControls {
    private static final double corners = 8;
    private static final Background defautlBackg = new Background(new BackgroundFill(MainView.THIRD_COLOR, new CornerRadii(corners), Insets.EMPTY));
    private static final Background hoverBackg = new Background(new BackgroundFill(MainView.MAIN_COLOR, new CornerRadii(corners), Insets.EMPTY));
    public static Button createButton(String label, double buttonWith, Font font){
        Button butt = new Button(label);
        butt.setPrefWidth(buttonWith);
        butt.setFont(font);
        butt.setCursor(Cursor.HAND);

        butt.setBackground(defautlBackg);
        butt.setTextFill(MainView.MAIN_COLOR);
        butt.setOnMouseEntered(e -> {
            butt.setBackground(new Background(new BackgroundFill(MainView.MAIN_COLOR, new CornerRadii(corners), Insets.EMPTY)));
            butt.setTextFill(MainView.THIRD_COLOR);
        });
        butt.setOnMouseExited(e -> {
            butt.setBackground(defautlBackg);
            butt.setTextFill(MainView.MAIN_COLOR);
        });
        return butt;
    }

    public static HBox createTitle(String text){
        Text title = new Text(text);
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        title.setFill(MainView.MAIN_COLOR);

        HBox titleContainer = new HBox(title);
        titleContainer.setPadding(new Insets(15));
        titleContainer.setAlignment(Pos.CENTER);

        return titleContainer;
    }

    public static Dialog<VBox> createDialog(String buttName, String title, VBox node) {
        ButtonType done = new ButtonType(buttName, ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Dialog<VBox> dialog = new Dialog<>();
        dialog.setTitle(title);

        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().addAll(cancel, done);
        pane.setPrefSize(400, 300);
        pane.setPadding(new Insets(12));
        pane.setBackground(new Background(new BackgroundFill(MainView.SECONDARY_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        Button buttDone = (Button) pane.lookupButton(done);
        Button buttCancel = (Button) pane.lookupButton(cancel);
        Button[] buttons = {buttDone, buttCancel};

        for (Button b : buttons) {
            b.setCursor(Cursor.HAND);
            b.setBackground(new Background(new BackgroundFill(MainView.THIRD_COLOR, new CornerRadii(8), Insets.EMPTY)));
            b.setFont(MainView.SECONDARY_FONT);
            b.setTextFill(MainView.MAIN_COLOR);
            b.setDisable(false);
        }

        buttDone.setOnMouseEntered(e -> {
            buttDone.setBackground(hoverBackg);
            buttDone.setTextFill(MainView.THIRD_COLOR);
        });
        buttDone.setOnMouseExited(e -> {
            buttDone.setBackground(defautlBackg);
            buttDone.setTextFill(MainView.MAIN_COLOR);
        });

        buttCancel.setOnMouseEntered(e -> {
            buttCancel.setBackground(hoverBackg);
            buttCancel.setTextFill(MainView.THIRD_COLOR);
        });
        buttCancel.setOnMouseExited(e -> {
            buttCancel.setBackground(defautlBackg);
            buttCancel.setTextFill(MainView.MAIN_COLOR);
        });

        VBox dialogBox = new VBox();
        dialogBox.getChildren().add(node);

        pane.setContent(dialogBox);

        return dialog;

    }

    public static HBox createInputField(String name){
        Label label = new Label(name + ": ");
        label.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        TextField field = new TextField();
        field.setFocusTraversable(true);
        HBox hBox = new HBox(label, field);
        hBox.setSpacing(11);

        return hBox;
    }

}
