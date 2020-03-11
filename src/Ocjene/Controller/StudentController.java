package Ocjene.Controller;

import Ocjene.Model.Baza;
import Ocjene.Model.OcjeneModel;
import com.mysql.jdbc.log.Log;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

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
    TextField PredmetField;
    @FXML
    Label ProsjekLabel;
    @FXML
    TableColumn IDCol;
    @FXML
    ChoiceBox<String> cb;

    public void initialize(URL url, ResourceBundle rb ){


        ObservableList<OcjeneModel> data = null;
        try {
            data = OcjeneModel.listaOcjena(LoginController.ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IDCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("ID"));
        DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
        ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
        PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
        OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
        OcjeneTab.setItems(data);
        ObservableList<String> Predmeti;

        try {
            Predmeti = OcjeneModel.listaPredmeta(LoginController.ID);
            cb.getItems().setAll(Predmeti);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ;
    }

    @FXML
    public void izracunajProsjekPredmet(ActionEvent e) throws SQLException {
        String Predmet = this.cb.getValue();
        Baza DB = new Baza();;
        PreparedStatement as = DB.exec("SELECT * FROM ocjene WHERE Predmet = ? AND IDKorisnik= ?");
        as.setString(1,Predmet);
        as.setInt(2, LoginController.ID);
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
        as.setInt(2,LoginController.ID);
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
        String listaPredmeta[] = OcjeneModel.listaPredmeta(LoginController.ID).toArray(new String[OcjeneModel.setSize]);
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
    public void filterPredmete() throws SQLException {
        String Predmet = this.cb.getValue();
        ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena(LoginController.ID, Predmet);
        IDCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("ID"));
        DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel,String>("Datum"));
        ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
        PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
        OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
        OcjeneTab.setItems(data);
    }
    @FXML
    public void sveOcjene(){
        initialize(null,null);
    }
    }








