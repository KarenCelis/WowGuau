package com.example.wowguauv2;

public class Usuario {

    private String nombre;
    private String correo;
    private Integer edad;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private String pathFoto;
    private String tipo;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, Integer edad, String direccion, Double latitud, Double longitud, String pathFoto, String tipo) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.pathFoto = pathFoto;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
