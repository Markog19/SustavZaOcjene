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
    SimpleStringProperty ID = new SimpleStringProperty();
    SimpleStringProperty korisnickoIme = new SimpleStringProperty();
    SimpleStringProperty lozinka = new SimpleStringProperty();
    SimpleStringProperty email = new SimpleStringProperty();

    public KorisnikModel(String ID, String korisnickoIme, String lozinka, String email){
        this.ID = new SimpleStringProperty(ID);
        this.korisnickoIme = new SimpleStringProperty(korisnickoIme);
        this.lozinka = new SimpleStringProperty(lozinka);
        this.email = new SimpleStringProperty(email);

    }
    public String getID() {
        return ID.get();
    }

    public String getKorisnickoIme() {
        return korisnickoIme.get();
    }


    public void setID(String ID) {
        this.ID.set(ID);
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme.set(korisnickoIme);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setLozinka(String lozinka) {
        this.lozinka.set(lozinka);
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
                lista.add(new KorisnikModel(rs.getString("ID"),rs.getString("korisnicko_ime"), rs.getString("lozinka"), rs.getString("email")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
    public static ObservableList<KorisnikModel> listaStudenata1 () throws SQLException {
        ObservableList<KorisnikModel> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        PreparedStatement as = DB.exec("SELECT * FROM korisnik WHERE isAdmin = 0");
        ResultSet rs = as.executeQuery();
        try {
            while (rs.next()) {
                lista.add(new KorisnikModel(rs.getString("ID"),rs.getString("korisnicko_ime"), rs.getString("lozinka"), rs.getString("email")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
    public static ObservableList<String> listaStudenata () {
        ObservableList<String> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM korisnik WHERE isAdmin = 0");
        try {
            while (rs.next()) {
                lista.add(rs.getString("korisnicko_ime"));
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
    public void brisi (String ID) {
        try {
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("DELETE FROM korisnik WHERE ID=?");
            upit.setString(1, ID);
            upit.executeUpdate();
            PreparedStatement as = DB.exec("DELETE FROM ocjene WHERE IDKorisnik = ?");
            as.setString(1,ID);
            as.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu:" + ex.getMessage());
        }
    }
    public void uredi (String ID) {
        try {
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("UPDATE korisnik SET  korisnicko_ime=?, lozinka=?, email=? WHERE ID = ?");
            upit.setString(1, this.getKorisnickoIme());
            upit.setString(2, this.getLozinka());
            upit.setString(3, this.getEmail());
            upit.setString(4, ID);
            upit.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu:" + ex.getMessage());
        }
    }
}
