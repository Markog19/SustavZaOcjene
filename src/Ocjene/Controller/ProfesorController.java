package Ocjene.Controller;

import Ocjene.Model.Baza;
import Ocjene.Model.KorisnikModel;
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
    TableColumn EmailCol;
    @FXML
    TableColumn LozinkaCol;
    @FXML
    TableColumn IDCol1;
    @FXML
    TableColumn StudentCol;
    @FXML
    Button spremi;
    @FXML
    Button izaberiButton;
    @FXML
    Label StudentLabel;
    @FXML
    Label ProsjekLabel;
    @FXML
    Label IDLabel;
    @FXML
    TableColumn IDCol;
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    ChoiceBox<String> cb;
    @FXML
    Label StudentLabel1;
    @FXML
    TextField ImeField;
    @FXML
    TextField EmailField;
    @FXML
    TextField LozinkaField;
    @FXML
    TableView StudentiTab;
    public static int IDKorisnik = 2;
    public static String imeStudenta;
    OcjeneModel odabraniKontakt;
    KorisnikModel odabraniStudent;

    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> data = KorisnikModel.listaStudenata();
        choiceBox.setItems(data);


        ObservableList<String> Predmeti;
        try {
            Predmeti = OcjeneModel.listaPredmeta(IDKorisnik);
            cb.setItems(Predmeti);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<KorisnikModel> student;
        try {
            student = KorisnikModel.listaStudenata1();
            IDCol1.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("ID"));
            StudentCol.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("korisnickoIme"));
            LozinkaCol.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("lozinka"));
            EmailCol.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("email"));
            StudentiTab.setItems(student);
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
        OcjeneModel novi = new OcjeneModel(null, Datum, imeStudenta, Profesor, Predmet, Ocjena, IDKorisnik);
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
    public void dodajStudenta(ActionEvent e) throws SQLException {
        String Ime = this.ImeField.getText();
        String Email = this.EmailField.getText();
        String Lozinka = this.LozinkaField.getText();
        KorisnikModel novi = new KorisnikModel(null, Ime, Email, Lozinka);
        novi.spasi();
        ObservableList<String> data = KorisnikModel.listaStudenata();
        choiceBox.setItems(data);
        ImeField.clear();
        EmailField.clear();
        LozinkaField.clear();
        ObservableList<KorisnikModel> student = KorisnikModel.listaStudenata1();
        StudentiTab.setItems(student);

        IDCol1.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("ID"));
        StudentCol.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("korisnickoIme"));
        LozinkaCol.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("lozinka"));
        EmailCol.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("email"));


    }

    @FXML
    public void odaberiStudent(ActionEvent e) throws SQLException {

        String Student = this.choiceBox.getValue();
        Baza DB = new Baza();
        imeStudenta = Student;

        PreparedStatement as = DB.exec("SELECT * FROM korisnik WHERE korisnicko_ime = ?");
        try {
            as.setString(1, Student);
            StudentLabel.setText(Student);
            ResultSet ad = as.executeQuery();
            while (ad.next()) {
                IDKorisnik = ad.getInt("ID");
                ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(IDKorisnik);
                IDCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("ID"));
                DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
                ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
                PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
                OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
                OcjeneTab.setItems(data);
                ObservableList<String> Predmeti = OcjeneModel.listaPredmeta(IDKorisnik);
                cb.getItems().setAll(Predmeti);

            }
            ;
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }

    @FXML
    public void izracunajProsjekPredmet(ActionEvent e) throws SQLException {
        String Predmet = this.cb.getValue();
        Baza DB = new Baza();
        PreparedStatement as = DB.exec("SELECT * FROM ocjene WHERE Predmet = ? AND IDKorisnik= ?");
        as.setString(1, Predmet);
        as.setInt(2, IDKorisnik);
        ResultSet ad = as.executeQuery();
        int brojac = 0, zbroj = 0;

        while (ad.next()) {
            int ocjena;
            ocjena = ad.getInt("ocjena");
            zbroj = zbroj + ocjena;
            brojac++;
        }
        float prosjek;
        prosjek = (float) zbroj / brojac;
        String pro = "Prosjek je " + prosjek;
        ProsjekLabel.setText(pro);

    }

    public int izracunajProsjek(ActionEvent es, String Predmet) throws SQLException {
        Baza DB = new Baza();

        PreparedStatement as = DB.exec("SELECT * FROM ocjene WHERE Predmet = ? AND IDKorisnik= ?");
        as.setString(1, Predmet);
        as.setInt(2, IDKorisnik);
        ResultSet ad = as.executeQuery();
        int brojac = 0, zbroj = 0;

        while (ad.next()) {
            int ocjena;
            ocjena = ad.getInt("ocjena");
            zbroj = zbroj + ocjena;
            brojac++;
        }
        float prosjek;
        prosjek = (float) zbroj / brojac;
        int pro = Math.round(prosjek);
        System.out.println(pro);
        return pro;
    }

    @FXML
    public void izracunajUkProsjek(ActionEvent e) throws SQLException {
        String listaPredmeta[] = OcjeneModel.listaPredmeta(IDKorisnik).toArray(new String[OcjeneModel.setSize]);
        int zbroj = 0;
        for (int i = 0; i < listaPredmeta.length; i++) {
            zbroj = zbroj + izracunajProsjek(null, listaPredmeta[i]);
        }
        float prosjek;
        prosjek = (float) zbroj / OcjeneModel.setSize;
        String proText = "Ukupni prosjek je " + prosjek;
        ProsjekLabel.setText(proText);

    }

    @FXML
    public void ocjeneIzPredmeta(ActionEvent e) throws SQLException {
        String Predmet = this.cb.getValue();


        ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(IDKorisnik, Predmet);
        DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
        ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
        PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
        OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
        OcjeneTab.setItems(data);
    }

    @FXML
    public void odaberiKorisnika(Event e) {
        this.odabraniKontakt = (OcjeneModel) this.OcjeneTab.getSelectionModel().getSelectedItem();
        this.IDLabel.setText(this.odabraniKontakt.getID());
        this.DatumField.setText(this.odabraniKontakt.getDatum());
        this.PredmetField.setText(this.odabraniKontakt.getPredmet());
        this.OcjenaField.setText(this.odabraniKontakt.getOcjena());
    }

    @FXML
    public void urediOcjenu(Event e) throws SQLException {
        this.odabraniKontakt.setDatum(this.DatumField.getText());
        this.odabraniKontakt.setPredmet(this.PredmetField.getText());
        this.odabraniKontakt.setOcjena(this.OcjenaField.getText());
        this.odabraniKontakt.uredi(IDLabel.getText());
        ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(IDKorisnik);
        this.OcjeneTab.setItems(data);
        ObservableList<String> Predmeti = OcjeneModel.listaPredmeta(IDKorisnik);
        cb.setItems(Predmeti);
    }

    @FXML
    public void obrisiOcjenu(Event e) throws SQLException {
        if (this.odabraniKontakt != null) {
            this.odabraniKontakt.brisi(IDLabel.getText());
            ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(IDKorisnik);
            IDCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("ID"));
            DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
            ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
            PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
            OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
            OcjeneTab.setItems(data);
            ObservableList<String> Predmeti = OcjeneModel.listaPredmeta(IDKorisnik);
            cb.setItems(Predmeti);
        }
    }

    @FXML
    public void odaberiStudent1(Event e) {
        this.odabraniStudent = (KorisnikModel) this.StudentiTab.getSelectionModel().getSelectedItem();
        this.StudentLabel1.setText(this.odabraniStudent.getID());
        this.ImeField.setText(this.odabraniStudent.getKorisnickoIme());
        this.LozinkaField.setText(this.odabraniStudent.getLozinka());
        this.EmailField.setText(this.odabraniStudent.getEmail());
    }

    @FXML
    public void obrisiStudent(Event e) throws SQLException {
        if (this.odabraniStudent != null) {
            this.odabraniStudent.brisi(StudentLabel1.getText());

            ObservableList<KorisnikModel> data = KorisnikModel.listaStudenata1();
            IDCol1.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("ID"));
            StudentCol.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("korisnickoIme"));
            LozinkaCol.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("lozinka"));
            EmailCol.setCellValueFactory(new PropertyValueFactory<KorisnikModel, String>("email"));
            StudentiTab.setItems(data);
            ObservableList<String> data1 = KorisnikModel.listaStudenata();
            choiceBox.setItems(data1);
            IDCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("ID"));
            DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
            ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
            PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
            OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
            OcjeneTab.setItems(data);

        }
    }

    @FXML
    public void urediStudent(Event e) throws SQLException {

        this.odabraniStudent.setKorisnickoIme(this.ImeField.getText());
        this.odabraniStudent.setLozinka(this.LozinkaField.getText());
        this.odabraniStudent.setEmail(this.EmailField.getText());
        this.odabraniStudent.uredi(StudentLabel1.getText());
        ObservableList<KorisnikModel> student = KorisnikModel.listaStudenata1();
        StudentiTab.setItems(student);


    }
    @FXML
    public void sveOcjene() throws SQLException {
       odaberiStudent(null);
    }
}

