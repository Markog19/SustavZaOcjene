/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ocjene.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Ocjene.Model.Baza;
import Ocjene.Model.LogiraniKorisnikModel;
import Ocjene.Model.OcjeneModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Ocjene.Model.LogiraniKorisnikModel;

/**
 * FXML Controller class
 *

 */
public class LoginController  implements Initializable {
    public static int ID;
    public static String Profesor;

    @FXML
    Label statusLbl;

    @FXML
    TextField kimeTxt;

    @FXML
    PasswordField lozinkaTxt;


        @FXML
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    this.prijavise(null);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
   


    public void prijavise(ActionEvent e) throws SQLException, ClassNotFoundException {
        String kime = kimeTxt.getText();
        String lozinka = lozinkaTxt.getText();

        if (kime.equals("") || lozinka.equals("")) {
            statusLbl.setText("Morate unijeti sve vrijednosti!");
        } else {
            if (LogiraniKorisnikModel.logiraj(kime, lozinka)) {
                try {
                    Baza DB = new Baza();
                    statusLbl.setTextFill(Color.GREEN);
                    statusLbl.setText("Uspješno ste se prijavili");
                    PreparedStatement ps = DB.exec("SELECT * FROM korisnik WHERE korisnicko_ime =?");
                    ps.setString(1,kime);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt("isAdmin");
                        ID = rs.getInt("ID");
                        if (id == 1) {
                            Profesor = kime;
                            Parent root;
                            root = FXMLLoader.load(getClass().getClassLoader().getResource("Ocjene/View/Profesor.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Prikaz studenta");
                            stage.setScene(new Scene(root, 1120, 600));
                            stage.show();
                        } if(id == 0) {
                            Parent root;
                            root = FXMLLoader.load(getClass().getClassLoader().getResource("Ocjene/View/Student.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Prikaz studenta");
                            stage.setScene(new Scene(root, 600, 600));
                            stage.show();
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                statusLbl.setText("Korisnicki podatci nisu ispravni!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        // TODO
    }

}







