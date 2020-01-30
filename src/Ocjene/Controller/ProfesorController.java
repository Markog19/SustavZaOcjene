package Ocjene.Controller;

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


    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(OcjeneModel.listaOcjena());
        ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena();
        DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
        ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
        PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
        OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
        OcjeneTab.setItems(data);
    }

    @FXML
    public void dodajOcjenu(ActionEvent e) {
        String Datum = this.DatumField.getText();
        String Profesor = this.ProfesorField.getText();
        String Predmet = this.PredmetField.getText();
        String Ocjena = this.OcjenaField.getText();
        OcjeneModel novi = new OcjeneModel(Datum, Profesor, Predmet, Ocjena);
        novi.spasi();
        ObservableList<OcjeneModel> data = OcjeneModel.listaOcjena();
        DatumCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Datum"));
        ProfesorCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Profesor"));
        PredmetCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Predmet"));
        OcjenaCol.setCellValueFactory(new PropertyValueFactory<OcjeneModel, String>("Ocjena"));
        OcjeneTab.setItems(data);
        }
}
