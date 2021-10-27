package com.example.footboocking;

public class Product {
    private int id;
    private  int idLocal;
    private int precio;
    private  String nombre;
    private  String disponible;

    public Product(int id, int idLocal, int precio, String nombre, String disponible) {
        this.id = id;
        this.idLocal = idLocal;
        this.precio = precio;
        this.nombre = nombre;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public int getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDisponible() {
        return disponible;
    }
}