package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.views.CommonsUIControls;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Quotes extends VBox {

    public Quotes(){
        HBox title = CommonsUIControls.createTitle("Cotizaciones");
        getChildren().add(title);
    }

}












