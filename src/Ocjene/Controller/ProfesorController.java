package Ocjene.Controller;

import Ocjene.Model.Baza;
import Ocjene.Model.KorisnikModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Ocjene.Model.OcjeneModel;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;;

public class ProfesorController implements Initializable {
    @FXML
    TableView OcjeneTab;
    @FXML
    TableColumn DatumCol;
    @FXML
    TableColumn ProfesorCol;
    @FXML
    TableColumn PredmetCol;
    @FXML
    TableColumn OcjenaCol;
    @FXML
    Button spremiBtn;
    @FXML
    TextField DatumField;
    @FXML
    TextField ProfesorField;
    @FXML
    TextField PredmetField;
    @FXML
    TextField OcjenaField;
    @FXML
    TextField StudentField;
    @FXML
    TextField ImeCol;
    @FXML
    TextField EmailCol;
    @FXML
    TextField LozinkaCol;
    @FXML
    Button spremi;

    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<OcjeneModel> data = null;
        try {
            data = OcjeneModel.listaOcjena(LoginController.ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
        ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
        PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
        OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
        OcjeneTab.setItems(data);
    }

    @FXML
    public void dodajOcjenu(ActionEvent e) throws SQLException {
        String Datum = this.DatumField.getText();
        String Profesor = this.ProfesorField.getText();
        String Predmet = this.PredmetField.getText();
        String Ocjena = this.OcjenaField.getText();
        String Student = this.StudentField.getText();
        Baza DB = new Baza();
        PreparedStatement rs = DB.exec("SELECT * FROM korisnik WHERE korisnicko_ime = ?");
        rs.setString(1,Student);
        ResultSet ps = rs.executeQuery();
        try {
            while (ps.next()) {
                String ID = ps.getString("IDKorisnik");
                OcjeneModel novi = new OcjeneModel(Datum, Profesor, Predmet, Ocjena,ID);
                novi.spasi();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(LoginController.ID);
        DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
        ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
        PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
        OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
        OcjeneTab.setItems(data);
        }
    @FXML
        public void dodajStudenta(ActionEvent e){
        String Ime = this.ImeCol.getText();
        String Email = this.EmailCol.getText();
        String Lozinka = this.LozinkaCol.getText();
        KorisnikModel novi = new KorisnikModel(Ime, Email,Lozinka);
        novi.spasi();


    }

}
