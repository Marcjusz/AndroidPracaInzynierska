package com.ommomm.t1;

public class BazaOferta {

    String ilosc;
    String cena_regularna;
    String czas_przygotowania;
    String data_odbioru;
    String data_odbioruK;
    String firma;
    String lokal;
    String nazwa;
    String opis;
    String rabat;
    String zdjecie;


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

    public String getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
    }

    public String getCena_regularna() {
        return cena_regularna;
    }

    public void setCena_regularna(String cena_regularna) {
        this.cena_regularna = cena_regularna;
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

    public String getData_odbioruK() {
        return data_odbioruK;
    }

    public void setData_odbioruK(String data_odbioruK) {
        this.data_odbioruK = data_odbioruK;
    }

    public String getIlosc() {
        return ilosc;
    }

    public void setIlosc(String ilosc) {
        this.ilosc = ilosc;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getLokal() {
        return lokal;
    }

    public void setLokal(String lokal) {
        this.lokal = lokal;
    }

    public BazaOferta(String nazwa, String opis, String rabat, String zdjecie, String cena_regularna, String czas_przygotowania, String data_odbioru, String data_odbioruK, String ilosc, String firma, String lokal) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.rabat = rabat;
        this.zdjecie = zdjecie;
        this.cena_regularna = cena_regularna;
        this.czas_przygotowania = czas_przygotowania;
        this.data_odbioru = data_odbioru;
        this.data_odbioruK = data_odbioruK;
        this.ilosc = ilosc;
        this.firma = firma;
        this.lokal = lokal;
    }

    BazaOferta(){

    }


}
