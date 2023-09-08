package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.views.CommonsUIControls;
import com.sasd.appcotizacion.views.MainView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Clients extends VBox {

    public Clients(){
        HBox title = CommonsUIControls.createTitle("Clientes");
        getChildren().add(title);

    }

}
