package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Controller {

    public Button btVentana;
    public Label lblVentana;
    public SubScene sceneSub;
    public Scene scene2;
    public Scene scene1;

    //region Controladores
@FXML
//endregion



    public void initialize() {
        /*createTree();
        txtTitulo.setText("");
        txtDefinicion.setText("");
        txtCodigo.setText("");*/
        //txtColor.setText("HOLA!");
    }

    public void abrirVentana() throws IOException {
        Stage stage= (Stage) btVentana.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Ventana.fxml"));
        stage.setTitle("Ventana");
        stage.setScene(new Scene(root,1200, 1100));
        stage.show();

        //scene2.getClass().getResource("Ventana.fxml");

        sceneSub.getClass().getResource("Ventana.fxml");

        stage.setTitle("Ventana");
        stage.sce //.setScene(sceneSub);
        stage.show();

       // ((Pane) scene2.getRoot()).getChildren().add(scene1.getRoot());

    }




}
