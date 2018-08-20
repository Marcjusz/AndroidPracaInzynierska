package com.ommomm.t1;

import java.io.Serializable;

public class BazaLokal implements Serializable{
    String nazwa;
    String opis;
    String telefon1;
    String telefon2;
    String adres;
    String photo1;
    String photo2;
    String photo3;

    BazaLokal(){

    }

    public BazaLokal(String nazwa, String opis, String telefon1, String telefon2, String adres, String photo1, String photo2, String photo3) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.telefon1 = telefon1;
        this.telefon2 = telefon2;
        this.adres = adres;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTelefon1() {
        return telefon1;
    }

    public void setTelefon1(String telefon1) {
        this.telefon1 = telefon1;
    }

    public String getTelefon2() {
        return telefon2;
    }

    public void setTelefon2(String telefon2) {
        this.telefon2 = telefon2;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }
}
