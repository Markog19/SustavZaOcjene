package Ocjene.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KorisnikModel {
    SimpleStringProperty korisnickoIme = new SimpleStringProperty();
    SimpleStringProperty email = new SimpleStringProperty();
    SimpleStringProperty lozinka = new SimpleStringProperty();

    public KorisnikModel(String korisnickoIme, String email, String lozinka){
        this.korisnickoIme = new SimpleStringProperty(korisnickoIme);
        this.email = new SimpleStringProperty(email);
        this.lozinka = new SimpleStringProperty(lozinka);
    }
    public String getKorisnickoIme() {
        return korisnickoIme.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getLozinka() {
        return lozinka.get();
    }

    public static ObservableList<KorisnikModel> listaKorisnika () {
        ObservableList<KorisnikModel> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM korisnik");
        try {
            while (rs.next()) {
                lista.add(new KorisnikModel(rs.getString("korisnicko_ime"), rs.getString("lozinka"), rs.getString("email")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
    public void spasi () {
        Baza DB = new Baza();
        PreparedStatement insert = DB.exec("INSERT INTO korisnik VALUES(null,?,?,?,0)");
        try {
            insert.setString(1, this.korisnickoIme.getValue());
            insert.setString(2, this.email.getValue());
            insert.setString(3, this.lozinka.getValue());
            insert.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OcjeneModel.class.getName()).log(Level.SEVERE, null,
                    ex);
        }

    }
}
