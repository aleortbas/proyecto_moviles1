package com.example.footboocking;

import java.security.PrivateKey;

public class cancha {
    private int id, id_local;
    private String disponible, nombre, image;

    public cancha(int id, int id_local, String disponible, String nombre, String image) {
        this.id = id;
        this.id_local = id_local;
        this.disponible = disponible;
        this.nombre = nombre;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getId_local() {
        return id_local;
    }

    public String getDisponible() {
        return disponible;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImage() {
        return image;
    }
}
