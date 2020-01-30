/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ocjene.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Ocjene.Model.Baza;
import Ocjene.Model.LogiraniKorisnikModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Ocjene.Model.LogiraniKorisnikModel;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class LoginController implements Initializable {

    @FXML
    Label statusLbl;

    @FXML
    TextField kimeTxt;

    @FXML
    PasswordField lozinkaTxt;


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
                        if (id == 1) {
                            Parent root;
                            root = FXMLLoader.load(getClass().getClassLoader().getResource("Ocjene/View/Profesor.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Prikaz svih studenata u Bazi podataka");
                            stage.setScene(new Scene(root, 450, 450));
                            stage.show();
                        } else {

                            Parent root;
                            root = FXMLLoader.load(getClass().getClassLoader().getResource("Ocjene/View/Student.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Prikaz svih studenata u Bazi podataka");
                            stage.setScene(new Scene(root, 450, 450));
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







