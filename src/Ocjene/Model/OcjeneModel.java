package Ocjene.Model;

import Ocjene.Controller.LoginController;
import Ocjene.Controller.ProfesorController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import javax.security.auth.login.LoginContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.PropertyResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OcjeneModel {
    SimpleStringProperty ID = new SimpleStringProperty();
    SimpleStringProperty Datum = new SimpleStringProperty();
    SimpleStringProperty Ime = new SimpleStringProperty();
    SimpleStringProperty Profesor = new SimpleStringProperty();
    SimpleStringProperty Predmet = new SimpleStringProperty();
    SimpleStringProperty Ocjena = new SimpleStringProperty();
    SimpleIntegerProperty IDKorisnik = new SimpleIntegerProperty();


    public OcjeneModel (String ID, String Datum,String Ime, String Profesor, String Predmet, String Ocjena, Integer IDKorisnik) {
        this.ID = new SimpleStringProperty(ID);
        this.Datum = new SimpleStringProperty (Datum);
        this.Ime = new SimpleStringProperty(Ime);
        this.Profesor = new SimpleStringProperty(Profesor);
        this.Predmet = new SimpleStringProperty(Predmet);
        this.Ocjena = new SimpleStringProperty(Ocjena);
        this.IDKorisnik = new SimpleIntegerProperty(IDKorisnik);
    }
    public String getDatum() {
        return Datum.get();
    }
    public String getID(){ return ID.get();}
    public String getProfesor() {
        return Profesor.get();
    }
    public void setID(String ID) {
        this.ID.set(ID);
    }


    public void setDatum(String datum) {
        this.Datum.set(datum);
    }

    public void setPredmet(String predmet) {
        this.Predmet.set(predmet);
    }

    public void setOcjena(String ocjena) {
        this.Ocjena.set(ocjena);
    }

    public String getPredmet() {
        return Predmet.get();
    }


    public String getOcjena() {
        return Ocjena.get();
    }
    public String getIme() {
        return Ime.get();
    }
    public static int setSize;
    public static ObservableList<OcjeneModel> listaOcjena (int ID) throws SQLException {
        ObservableList<OcjeneModel> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        PreparedStatement as = DB.exec("SELECT * FROM ocjene WHERE IDKorisnik = ? ");
        as.setInt(1,ID);

        ResultSet rs = as.executeQuery();
        try {
            while (rs.next()) {
                lista.add(new OcjeneModel(rs.getString("ID"),rs.getString("Datum"),rs.getString("Ime"), rs.getString("Profesor"), rs.getString("Predmet"), rs.getString("Ocjena"),rs.getInt("IDKorisnik")));

            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
    public static ObservableList<OcjeneModel> listaOcjena (int ID,String Predmet) throws SQLException {
        ObservableList<OcjeneModel> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        PreparedStatement as = DB.exec("SELECT * FROM ocjene WHERE IDKorisnik = ? AND Predmet = ?");
        as.setInt(1,ID);
        as.setString(2,Predmet);
        ResultSet rs = as.executeQuery();
        try {
            while (rs.next()) {
                lista.add(new OcjeneModel(rs.getString("ID"),rs.getString("Datum"),rs.getString("Ime"), rs.getString("Profesor"), rs.getString("Predmet"), rs.getString("Ocjena"),rs.getInt("IDKorisnik")));

            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
    public static ObservableList<String> listaPredmeta (int ID) throws SQLException {
        ObservableList<String> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        PreparedStatement as = DB.exec("SELECT COUNT(*) AS rowcount FROM ocjene WHERE IDKorisnik = ?");
        as.setInt(1,ID);
        ResultSet a = as.executeQuery();
        a.next();
        int vel = a.getInt("rowcount");
        PreparedStatement ad = DB.exec("SELECT * FROM ocjene WHERE IDKorisnik = ?");
        ad.setInt(1, ID);
        ResultSet rs = ad.executeQuery();
        Set<String> hash_Set = new HashSet<String>();
        try {
            while (rs.next()) {
                hash_Set.add(rs.getString("Predmet"));
            }

        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        setSize = hash_Set.size();
       lista.addAll(hash_Set);







        return lista;
    }

    public void spasi () {
        Baza DB = new Baza();
        PreparedStatement insert = DB.exec("INSERT INTO ocjene VALUES(null,?,?,?,?,?,?)");
        try {
            insert.setString(1, this.Datum.getValue());
            insert.setString(2,this.Ime.getValue());
            insert.setString(3, this.Profesor.getValue());
            insert.setString(4, this.Predmet.getValue());
            insert.setString(5, this.Ocjena.getValue());
            insert.setInt(6, this.IDKorisnik.getValue());

            insert.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OcjeneModel.class.getName()).log(Level.SEVERE, null,
                    ex);
        }

     }

    public void uredi (String ID) {
        try {
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("UPDATE ocjene SET  Datum=?, Predmet=?, Ocjena=? WHERE ID = ?");
            upit.setString(1, this.getDatum());
            upit.setString(2, this.getPredmet());
            upit.setString(3, this.getOcjena());
            upit.setString(4, ID);
            upit.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu:" + ex.getMessage());
        }
    }

    public void brisi (String ID) {
        try {
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("DELETE FROM ocjene WHERE ID=?");
            upit.setString(1, ID);
            upit.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu:" + ex.getMessage());
        }
    }


}
