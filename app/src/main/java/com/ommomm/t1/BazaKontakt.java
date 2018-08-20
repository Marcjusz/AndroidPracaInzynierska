package com.ommomm.t1;

public class BazaKontakt {

    String id;
    String kontakt;
    String tresc;

    BazaKontakt(){

    }

    public BazaKontakt(String id, String kontakt, String tresc) {
        this.id = id;
        this.kontakt = kontakt;
        this.tresc = tresc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }
}
