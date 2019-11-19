package com.example.wowguauv2;

import java.io.Serializable;
import java.util.Date;

public class Paseo implements Serializable {

    private boolean calificado;
    private boolean aceptado;
    private boolean activo;
    private String clienteUid;
    private String paseadorCorreo;
    private String nombreMascota;
    private double latPaseador;
    private double longPaseador;
    private Date inicio;
    private Date fin;

    public Paseo() {
    }

    public boolean isCalificado() {
        return calificado;
    }

    public void setCalificado(boolean calificado) {
        this.calificado = calificado;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getClienteUid() {
        return clienteUid;
    }

    public void setClienteUid(String clienteUid) {
        this.clienteUid = clienteUid;
    }

    public String getPaseadorCorreo() {
        return paseadorCorreo;
    }

    public void setPaseadorCorreo(String paseadorCorreo) {
        this.paseadorCorreo = paseadorCorreo;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public double getLatPaseador() {
        return latPaseador;
    }

    public void setLatPaseador(double latPaseador) {
        this.latPaseador = latPaseador;
    }

    public double getLongPaseador() {
        return longPaseador;
    }

    public void setLongPaseador(double longPaseador) {
        this.longPaseador = longPaseador;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }
}
