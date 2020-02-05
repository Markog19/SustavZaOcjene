package Ocjene.Controller;

import Ocjene.Model.Baza;
import Ocjene.Model.OcjeneModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    public void initialize(URL url, ResourceBundle rb ){
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM LogiraniKorisnik");
        try {
            while (rs.next()) {
                int ID = rs.getInt("IDKorisnik");
                PreparedStatement bs = DB.exec("DELETE  FROM LogiraniKorisnik WHERE IDKorisnik =?");
                bs.setInt(1,ID);
                bs.executeUpdate();
                ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena();
                for(OcjeneModel item:data){
                    System.out.println(item);
                }
                DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
                ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
                PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
                OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
                OcjeneTab.setItems(data);
                }
            } catch (SQLException ex) {
            ex.printStackTrace();
        }






    }



}
