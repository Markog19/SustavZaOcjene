package Ocjene.Controller;

import Ocjene.Model.Baza;
import Ocjene.Model.KorisnikModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import Ocjene.Model.OcjeneModel;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    TextField PredmetField;
    @FXML
    TextField OcjenaField;
    @FXML
    TextField StudentField;
    @FXML
    TextField ImeField;
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
    @FXML
    TextField Predmet;
    @FXML
    Label IDLabel;
    @FXML
    TableColumn IDCol;
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    ChoiceBox<String> cb;

    public static int IDKorisnik = 2;
    public static String imeStudenta;
    OcjeneModel odabraniKontakt;

    public void initialize(URL url, ResourceBundle rb) {
    ObservableList <String> data = KorisnikModel.listaStudenata();
        choiceBox.setItems(data);


        ObservableList<String>Predmeti;
        try {
            Predmeti = OcjeneModel.listaPredmeta(IDKorisnik);
            cb.setItems(Predmeti);

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }




    @FXML
    public void dodajOcjenu(ActionEvent e) throws SQLException {
        String Datum = this.DatumField.getText();
        String Profesor = LoginController.Profesor;
        String Predmet = this.PredmetField.getText();
        String Ocjena = this.OcjenaField.getText();
        System.out.println(IDKorisnik);
        OcjeneModel novi = new OcjeneModel(null,Datum, imeStudenta, Profesor, Predmet, Ocjena,IDKorisnik);
        novi.spasi();


        ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(IDKorisnik);
        IDCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("ID"));
        DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
        ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
        PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
        OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
        OcjeneTab.setItems(data);
        DatumField.clear();
        PredmetField.clear();
        OcjenaField.clear();
        ObservableList<String> Predmeti = OcjeneModel.listaPredmeta(IDKorisnik);
        cb.setItems(Predmeti);

        }
    @FXML
        public void dodajStudenta(ActionEvent e){
        String Ime = this.ImeField.getText();
        String Email = this.EmailCol.getText();
        String Lozinka = this.LozinkaCol.getText();
        KorisnikModel novi = new KorisnikModel(Ime, Email,Lozinka);
        novi.spasi();
        ObservableList <String> data = KorisnikModel.listaStudenata();
        choiceBox.setItems(data);
        ImeField.clear();
        EmailCol.clear();
        LozinkaCol.clear();


    }
    @FXML
    public void odaberiStudent(ActionEvent e) throws SQLException {

        String Student = this.choiceBox.getValue();
        Baza DB = new Baza();
        imeStudenta = Student;

        PreparedStatement as = DB.exec("SELECT * FROM korisnik WHERE korisnicko_ime = ?");
        try {
            as.setString(1,Student);
            StudentLabel.setText(Student);
            ResultSet ad = as.executeQuery();
            while (ad.next()) {
                 IDKorisnik = ad.getInt("ID");
               ObservableList<String> Predmeti = OcjeneModel.listaPredmeta(IDKorisnik);
                cb.setItems(Predmeti);
                ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(IDKorisnik);
                IDCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("ID"));
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
    }
    @FXML
    public void izracunajProsjekPredmet(ActionEvent e) throws SQLException {
        String Predmet = this.cb.getValue();
        Baza DB = new Baza();
        PreparedStatement as = DB.exec("SELECT * FROM ocjene WHERE Predmet = ? AND IDKorisnik= ?");
        as.setString(1,Predmet);
        as.setInt(2,IDKorisnik);
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

        PreparedStatement as = DB.exec("SELECT * FROM ocjene WHERE Predmet = ? AND IDKorisnik= ?");
        as.setString(1,Predmet);
        as.setInt(2,IDKorisnik);
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
        System.out.println(pro);
        return pro;
    }

    @FXML
    public void izracunajUkProsjek(ActionEvent e) throws SQLException {
       String listaPredmeta[] = OcjeneModel.listaPredmeta(IDKorisnik).toArray(new String[OcjeneModel.k + 1]);
       int zbroj = 0;
       for(int i = 0;i<listaPredmeta.length;i++){
           zbroj = zbroj + izracunajProsjek(null,listaPredmeta[i]);
       }
       float prosjek;
       prosjek = (float) zbroj/listaPredmeta.length;
       String proText = "Ukupni prosjek je " + prosjek;
       ProsjekLabel.setText(proText);

        }
        @FXML
    public  void ocjeneIzPredmeta(ActionEvent e) throws SQLException {
                String Predmet = this.cb.getValue();


            ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(IDKorisnik,Predmet);
            DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
            ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
            PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
            OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
            OcjeneTab.setItems(data);
        }

    @FXML
    public void odaberiKorisnika (Event e) {
        this.odabraniKontakt = (OcjeneModel)this.OcjeneTab.getSelectionModel().getSelectedItem();
        this.IDLabel.setText(this.odabraniKontakt.getID());
        this.DatumField.setText(this.odabraniKontakt.getDatum());
        this.PredmetField.setText(this.odabraniKontakt.getPredmet());
        this.OcjenaField.setText(this.odabraniKontakt.getOcjena());
    }
    @FXML
    public void urediKontakt(Event e) throws SQLException {
        this.odabraniKontakt.setDatum(this.DatumField.getText());
        this.odabraniKontakt.setPredmet(this.PredmetField.getText());
        this.odabraniKontakt.setOcjena(this.OcjenaField.getText());
        this.odabraniKontakt.uredi(IDLabel.getText());
        ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(IDKorisnik);
        this.OcjeneTab.setItems(data);
    }
    @FXML
    public void brisiKontakt(Event  e) throws SQLException {
        if (this.odabraniKontakt != null) {
            this.odabraniKontakt.brisi(IDLabel.getText());
            ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(IDKorisnik);
            IDCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("ID"));
            DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
            ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
            PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
            OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
            OcjeneTab.setItems(data);
        }
    }

}

