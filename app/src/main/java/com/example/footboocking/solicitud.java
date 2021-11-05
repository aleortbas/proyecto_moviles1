package com.example.footboocking;

public class solicitud {
    private int id, identificacion,id_usuario, estado;
    private String nombre, tipo_identificacion, camara_comercio, foto;

    public solicitud(int id, int identificacion, int id_usuario, String nombre, String tipo_identificacion, String camara_comercio, String foto, int estado) {
        this.id = id;
        this.identificacion = identificacion;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.tipo_identificacion = tipo_identificacion;
        this.camara_comercio = camara_comercio;
        this.foto = foto;
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }

    public String getFoto() {
        return foto;
    }

    public int getId() {
        return id;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo_identificacion() {
        return tipo_identificacion;
    }

    public String getCamara_comercio() {
        return camara_comercio;
    }
}
