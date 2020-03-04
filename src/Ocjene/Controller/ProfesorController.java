package Ocjene.Controller;

import Ocjene.Model.Baza;
import Ocjene.Model.KorisnikModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import javafx.scene.control.cell.PropertyValueFactory;;import static Ocjene.Controller.LoginController.ID;

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
    @FXML
    Button izaberiButton;
    @FXML
    Label StudentLabel;
    @FXML
    Label ProsjekLabel;

    public void initialize(URL url, ResourceBundle rb) {


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
        ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(ID);
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
    @FXML
    public int odaberiStudent(ActionEvent e){
        String Student = this.StudentField.getText();
        Baza DB = new Baza();
        int Id = 0;

        PreparedStatement as = DB.exec("SELECT * FROM korisnik WHERE korisnicko_ime = ?");
        try {
            as.setString(1,Student);
            StudentLabel.setText(Student);
            ResultSet ad = as.executeQuery();
            while (ad.next()) {
                 Id = ad.getInt("ID");
                ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(Id);
                DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
                ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
                PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
                OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
                OcjeneTab.setItems(data);
            }
             ;
        }

        catch (SQLException er) {
            er.printStackTrace();
        }

        return Id;
    }
    @FXML
    public void izracunajProsjekPredmet(ActionEvent e) throws SQLException {
        String Predmet = this.PredmetField.getText();
        Baza DB = new Baza();
        int ID = odaberiStudent(e);
        PreparedStatement as = DB.exec("SELECT * FROM ocjene WHERE Predmet = ? AND IDKorisnik= ?");
        as.setString(1,Predmet);
        as.setInt(2,ID);
        ResultSet ad = as.executeQuery();
        int brojac = 0,zbroj=0;

        while(ad.next()){
            int ocjena;
            ocjena = ad.getInt("ocjena");
            zbroj = zbroj + ocjena;
            brojac++;
        }
        float prosjek;
        prosjek = (float) zbroj/brojac;
        String pro = "Prosjek je " + prosjek;
        ProsjekLabel.setText(pro);
    }
    public int izracunajProsjek(ActionEvent es, String Predmet) throws SQLException {
        Baza DB = new Baza();
        int ID = odaberiStudent(es);
        PreparedStatement as = DB.exec("SELECT * FROM ocjene WHERE Predmet = ? AND IDKorisnik= ?");
        as.setString(1,Predmet);
        as.setInt(2,ID);
        ResultSet ad = as.executeQuery();
        int brojac = 0,zbroj=0;

        while(ad.next()){
            int ocjena;
            ocjena = ad.getInt("ocjena");
            zbroj = zbroj + ocjena;
            brojac++;
        }
        float prosjek;
        prosjek = (float) zbroj/brojac;
        int pro = Math.round(prosjek);
        return pro;
    }

    @FXML
    public void izracunajUkProsjek(ActionEvent e) throws SQLException {
       String listaPredmeta[] = new String[]{"Matematika","Povijest"};
       int zbroj = 0;
       for(int i = 0;i<listaPredmeta.length;i++){
           zbroj = zbroj + izracunajProsjek(e,listaPredmeta[i]);
       }
       float prosjek;
       prosjek = (float) zbroj/listaPredmeta.length;
       String proText = "Ukupni prosjek je " + prosjek;
       ProsjekLabel.setText(proText);

        }
}

