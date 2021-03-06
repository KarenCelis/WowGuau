package com.example.wowguauv2;

public class Paseador extends Usuario {

    private String descripcion;
    private Integer AñosE;
    private String Certificados;
    private boolean estado;
    private int paseosRealizados;
    private float calificacion;

    public Paseador() {
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getPaseosRealizados() {
        return paseosRealizados;
    }

    public void setPaseosRealizados(int paseosRealizados) {
        this.paseosRealizados = paseosRealizados;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public Paseador(String nombre, String correo, Integer edad, String direccion, Double latitud, Double longitud, String pathFoto, String tipo, String descripcion, Integer añosE, String certificados, boolean estado) {
        super(nombre, correo, edad, direccion, latitud, longitud, pathFoto, tipo);
        this.descripcion = descripcion;
        AñosE = añosE;
        Certificados = certificados;
        this.estado = estado;
    }

    public Paseador(String descripcion, Integer añosE, String certificados, boolean estado) {
        this.descripcion = descripcion;
        AñosE = añosE;
        Certificados = certificados;
        this.estado = estado;
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
