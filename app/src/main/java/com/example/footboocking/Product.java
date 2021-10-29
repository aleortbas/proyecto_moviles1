package com.example.footboocking;

public class Product {
    private int id;
    private  int idLocal;
    private  String nombre;
    private  String disponible;
    private  String image;

    public Product(int id, int idLocal, String nombre, String disponible, String image) {
        this.id = id;
        this.idLocal = idLocal;
        this.nombre = nombre;
        this.disponible = disponible;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDisponible() {
        return disponible;
    }

    public String getImage() {
        return image;
    }
}