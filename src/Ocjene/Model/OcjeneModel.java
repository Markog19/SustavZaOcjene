package Ocjene.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OcjeneModel {
    SimpleStringProperty Datum = new SimpleStringProperty();
    SimpleStringProperty Profesor = new SimpleStringProperty();
    SimpleStringProperty Predmet = new SimpleStringProperty();
    SimpleStringProperty Ocjena = new SimpleStringProperty();


    public OcjeneModel (String Datum, String Profesor, String Predmet, String Ocjena) {
        this.Datum = new SimpleStringProperty (Datum);
        this.Profesor = new SimpleStringProperty(Profesor);
        this.Predmet = new SimpleStringProperty(Predmet);
        this.Ocjena = new SimpleStringProperty(Ocjena);
    }
    public String getDatum() {
        return Datum.get();
    }

    public String getProfesor() {
        return Profesor.get();
    }



    public String getPredmet() {
        return Predmet.get();
    }

    public void setPredmet(String predmet) {
        this.Predmet.set(predmet);
    }

    public String getOcjena() {
        return Ocjena.get();
    }

    public static ObservableList<OcjeneModel> listaOcjena () {
        ObservableList<OcjeneModel> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM ocjene");
        try {
            while (rs.next()) {
                lista.add(new OcjeneModel(rs.getString("Datum"), rs.getString("Profesor"), rs.getString("Predmet"), rs.getString("Ocjena")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
    public void spasi () {
        Baza DB = new Baza();
        PreparedStatement insert = DB.exec("INSERT INTO ocjene VALUES(null,?,?,?,?)");
        try {
            insert.setString(1, this.Datum.getValue());
            insert.setString(2, this.Profesor.getValue());
            insert.setString(3, this.Predmet.getValue());
            insert.setString(4, this.Ocjena.getValue());
            insert.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OcjeneModel.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
}
