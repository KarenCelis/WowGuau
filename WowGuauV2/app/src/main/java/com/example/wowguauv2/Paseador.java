package com.example.wowguauv2;

public class Paseador extends Usuario {
    private String descripcion;
    private Integer AñosE;
    private String Certificados;

    public Paseador(String descripcion, Integer añosE, String certificados) {
        this.descripcion = descripcion;
        AñosE = añosE;
        Certificados = certificados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAñosE() {
        return AñosE;
    }

    public void setAñosE(Integer añosE) {
        AñosE = añosE;
    }

    public String getCertificados() {
        return Certificados;
    }

    public void setCertificados(String certificados) {
        Certificados = certificados;
    }
}
