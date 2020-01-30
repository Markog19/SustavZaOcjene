/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ocjene;

import java.io.IOException;
import java.sql.PreparedStatement;

import Ocjene.Model.Baza;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Daniel
 */
public class Ocjene extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Baza db = new Baza();
        PreparedStatement ps = db.exec("SELECT * FROM korisnik WHERE korisnicko_ime =? AND "
                + "lozinka=?");
        Parent root = FXMLLoader.load(getClass().getResource("View/Login.fxml"));

        Scene scene = new Scene(root, 460, 320);

        primaryStage.setTitle("Prijavite se na sustav!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
