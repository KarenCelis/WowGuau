package com.example.wowguauv2;


import java.io.Serializable;

public class Mascota implements Serializable {

    public enum Size{
        XSmall,
        Small,
        Medium,
        Large,
        XLarge
    }

    private String nombre;
    private String raza;
    private int edad;
    private String tamano;
    private String duenoUid;
    private String pathFoto;
    private String recomendaciones;

    public Mascota() {
    }

    public Mascota(String nombre, String raza, int edad, String tamano, String duenoUid, String pathFoto, String recomendaciones) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.tamano = tamano;
        this.duenoUid = duenoUid;
        this.pathFoto = pathFoto;
        this.recomendaciones = recomendaciones;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getDuenoUid() {
        return duenoUid;
    }

    public void setDuenoUid(String duenoUid) {
        this.duenoUid = duenoUid;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }
}

