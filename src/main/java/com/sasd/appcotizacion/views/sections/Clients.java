package com.sasd.appcotizacion.views.sections;

import com.sasd.appcotizacion.controllers.ClientsController;
import com.sasd.appcotizacion.models.ClientModel;
import com.sasd.appcotizacion.views.CommonsUIControls;
import com.sasd.appcotizacion.views.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Clients extends VBox {

    public Clients(){
        HBox title = CommonsUIControls.createTitle("Clientes");
        getChildren().add(title);

        Button addClient = CommonsUIControls.createButton("Crear Cliente", 200, MainView.SECONDARY_FONT);
        Button editClient = CommonsUIControls.createButton("Editar Cliente", 180, MainView.SECONDARY_FONT);
        Button removeClient = CommonsUIControls.createButton("Eliminar Cliente", 180, MainView.SECONDARY_FONT);

        HBox actionButtons = new HBox(removeClient, editClient, addClient);
        actionButtons.setAlignment(Pos.CENTER_RIGHT);
        actionButtons.setSpacing(8);
        actionButtons.setPadding(new Insets(10));
        getChildren().add(actionButtons);

        fieldsBox = new VBox(clientNameBox, phoneNumberBox);
        fieldsBox.setSpacing(10);

        TableColumn<ClientModel, Integer> clientId = new TableColumn<>("ID");
        TableColumn<ClientModel, String> clientName = new TableColumn<>("Nombre");
        TableColumn<ClientModel, String> clientPhone = new TableColumn<>("Telefono");
        clientName.setPrefWidth(150);
        clientPhone.setPrefWidth(150);

        clientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        clientsTable = new TableView<>();
        clientsTable.getColumns().addAll(clientId, clientName, clientPhone);
        getChildren().add(clientsTable);
        loadTableData();

        TextField[] fields = {clientNameField, clientPhoneField};
        for(TextField f: fields){
            f.setOnKeyPressed(ev -> {
                if(ev.getCode() == KeyCode.ENTER){
                    clientNameBox.requestFocus();
                }
            });
        }

        addClient.setOnMouseClicked(e -> {
            dialog = CommonsUIControls.createDialog("Crear", "Crear nuevo cliente", fieldsBox, true);
            doneButton = (Button) dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(1));
            validateDialogFields(doneButton);
            dialog.showAndWait().ifPresent(resp -> {
                if(resp.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                    setClientTable();
                    cleanDialog();
                }else {
                    cleanDialog();
                }
            });
        });

        editClient.setOnMouseClicked(e -> {
            ClientModel selectedClient = clientsTable.getSelectionModel().getSelectedItem();
            clientNameField.setText(selectedClient.getName());
            clientPhoneField.setText(selectedClient.getPhone());
            dialog = CommonsUIControls.createDialog("Editar", "Editar cliente", fieldsBox, true);
            doneButton = (Button) dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(1));
            validateDialogFields(doneButton);

            dialog.showAndWait().ifPresent(resp -> {
                if(resp.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                    updateSelectedClient(selectedClient.getId());
                    cleanDialog();
                }else {
                    cleanDialog();
                }
            });
        });

        removeClient.setOnMouseClicked(e -> {
            ClientModel selectedClient = clientsTable.getSelectionModel().getSelectedItem();
            Text text = new Text("Estas seguro de querer eliminar este cliente?");
            VBox msgBox = new VBox(text);
            dialog = CommonsUIControls.createDialog("Eliminar", "Eliminar cliente", msgBox, false);
            dialog.showAndWait().ifPresent(resp -> {
                if(resp.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                    deleteSelectedClient(selectedClient.getId());
                }
            });
        });

        editClient.disableProperty().bind(
                clientsTable.getSelectionModel().selectedItemProperty().isNull()
        );

        removeClient.disableProperty().bind(
                clientsTable.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    private void setClientTable(){
        String name = clientNameField.getText();
        String phone = clientPhoneField.getText();
        ClientModel newClient = new ClientModel(name, phone);
        ClientsController.createClient(newClient);
        clientsTable.getItems().clear();
        loadTableData();
        cleanDialog();
    }

    private void updateSelectedClient(int id){
        String name = clientNameField.getText();
        String phone = clientPhoneField.getText();
        ClientModel client = new ClientModel(id, name, phone);
        ClientsController.updateClient(client);
        clientsTable.getItems().clear();
        loadTableData();
        cleanDialog();
    }

    private void deleteSelectedClient(int id){
        ClientsController.deleteClient(id);
        clientsTable.getItems().clear();
        loadTableData();
    }

    private void loadTableData(){
        ResultSet rs = ClientsController.getAllClients();
        try {
            while (rs.next()){
                ClientModel client = new ClientModel(rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
                clientsTable.getItems().add(client);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void validateDialogFields(Button butt){

        TextField[] fields = {clientNameField, clientPhoneField};

        for(TextField f : fields){
            f.setOnKeyTyped(e -> {
                butt.setDisable(clientNameField.getText().isEmpty() || clientPhoneField.getText().isEmpty());
                if(f == clientPhoneField){
                    for (int i = 0; i < f.getText().length(); i++){
                        boolean isNum = Character.isDigit(f.getText().charAt(i));
                        if(!isNum) {
                            if(fieldsBox.getChildren().get(0).getTypeSelector().equals("Text")){
                                fieldsBox.getChildren().remove(0);
                            }
                            Text errorMsg = new Text("Este campo no puede contener letras");
                            fieldsBox.getChildren().add(0, errorMsg);
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

    private void cleanDialog(){
        clientNameField.clear();
        clientPhoneField.clear();
    }

    private TableView<ClientModel> clientsTable;
    private Dialog<ButtonType> dialog;
    private Button doneButton;
    private final VBox fieldsBox;
    private final HBox clientNameBox = CommonsUIControls.createInputField("Nombre(s) y Apellidos");
    private final HBox phoneNumberBox = CommonsUIControls.createInputField("Telefono");
    private final TextField clientNameField = (TextField) clientNameBox.getChildren().get(1);
    private final TextField clientPhoneField = (TextField) phoneNumberBox.getChildren().get(1);

}
