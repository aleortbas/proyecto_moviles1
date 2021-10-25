package com.example.footboocking;

import org.json.JSONException;
import org.json.JSONObject;

public class local {

    String id_admin, nombre, nombreAdmin, direccion, numeroCanchas, camaraComercio, email, clave;

    public local (JSONObject objetoJSON) throws JSONException {
        this.id_admin = objetoJSON.getString("id");
        this.nombre = objetoJSON.getString("nombre");
        this.nombreAdmin = objetoJSON.getString("nombre_administrador");
        this.direccion = objetoJSON.getString("direccion");
        this.numeroCanchas = objetoJSON.getString("numero_canchas");
        this.camaraComercio = objetoJSON.getString("camara_comercio");
        this.email = objetoJSON.getString("email");
        this.clave = objetoJSON.getString("clave");
    }

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumeroCanchas() {
        return numeroCanchas;
    }

    public void setNumeroCanchas(String numeroCanchas) {
        this.numeroCanchas = numeroCanchas;
    }

    public String getCamaraComercio() {
        return camaraComercio;
    }

    public void setCamaraComercio(String camaraComercio) {
        this.camaraComercio = camaraComercio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
