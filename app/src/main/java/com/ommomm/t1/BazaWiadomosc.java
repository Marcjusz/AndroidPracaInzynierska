package com.ommomm.t1;

public class BazaWiadomosc {

    String header;
    String body;
    String status;

    BazaWiadomosc(){

    }

    public BazaWiadomosc( String header, String body, String status) {
        this.header = header;
        this.body = body;
        this.status = status;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
