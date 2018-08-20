package com.ommomm.t1;

public class BazaOferta {
    String nazwa;
    String opis;
    String rabat;
    String kat;
    String zdjecie;
    String cena;
    String czas_przygotowania;
    String data_odbioru;
    String data_odbioruk;
    String ilosc;

    BazaOferta(){

    }

    public BazaOferta(String nazwa, String opis, String rabat, String kat, String zdjecie, String cena, String czas_przygotowania, String data_odbioru, String data_odbioruk, String ilosc) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.rabat = rabat;
        this.kat = kat;
        this.zdjecie = zdjecie;
        this.cena = cena;
        this.czas_przygotowania = czas_przygotowania;
        this.data_odbioru = data_odbioru;
        this.data_odbioruk = data_odbioruk;
        this.ilosc = ilosc;
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

    public String getRabat() {
        return rabat;
    }

    public void setRabat(String rabat) {
        this.rabat = rabat;
    }

    public String getKat() {
        return kat;
    }

    public void setKat(String kat) {
        this.kat = kat;
    }

    public String getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getCzas_przygotowania() {
        return czas_przygotowania;
    }

    public void setCzas_przygotowania(String czas_przygotowania) {
        this.czas_przygotowania = czas_przygotowania;
    }

    public String getData_odbioru() {
        return data_odbioru;
    }

    public void setData_odbioru(String data_odbioru) {
        this.data_odbioru = data_odbioru;
    }

    public String getData_odbioruk() {
        return data_odbioruk;
    }

    public void setData_odbioruk(String data_odbioruk) {
        this.data_odbioruk = data_odbioruk;
    }

    public String getIlosc() {
        return ilosc;
    }

    public void setIlosc(String ilosc) {
        this.ilosc = ilosc;
    }
}
