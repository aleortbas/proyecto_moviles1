package com.example.footboocking;

public class Product {
    private int id;
    private  int idLocal;
    private  String nombre;
    private  String disponible;

    public Product(int id, int idLocal, String nombre, String disponible) {
        this.id = id;
        this.idLocal = idLocal;
        this.nombre = nombre;
        this.disponible = disponible;
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
}