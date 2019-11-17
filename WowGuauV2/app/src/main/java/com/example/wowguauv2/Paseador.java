package com.example.wowguauv2;

public class Paseador extends Usuario {
    private String descripcion;
    private Integer AñosE;
    private String Certificados;

    public Paseador(String nombre, String correo, Integer edad, String direccion, Double latitud, Double longitud, String pathFoto, String tipo, String descripcion, Integer añosE, String certificados) {
        super(nombre, correo, edad, direccion, latitud, longitud, pathFoto, tipo);
        this.descripcion = descripcion;
        AñosE = añosE;
        Certificados = certificados;
    }

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