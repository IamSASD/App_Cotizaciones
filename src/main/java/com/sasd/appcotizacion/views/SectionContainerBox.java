package com.sasd.appcotizacion.views;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SectionContainerBox extends VBox {
    private final double BOX_WIDTH = 1100;
    public SectionContainerBox(VBox defaultSection){
        setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        setPrefWidth(BOX_WIDTH);
        getChildren().setAll(defaultSection);
    }
}
