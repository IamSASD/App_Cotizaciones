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

public class Quotations extends VBox {

    public Quotations(){
        HBox title = CommonsUIControls.createTitle("Cotizaciones");
        getChildren().add(title);
    }

}
