package Ocjene.Controller;

import Ocjene.Model.Baza;
import Ocjene.Model.OcjeneModel;
import com.mysql.jdbc.log.Log;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    }

    @FXML
    public void izracunajProsjekPredmet(ActionEvent e) throws SQLException {
        String Predmet = this.PredmetField.getText();
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




