package com.example.footboocking;

import org.json.JSONException;
import org.json.JSONObject;

public class usuario {

    String id_user, nombre, apellido, email, telefono, contraseña;

    public usuario(JSONObject objetoJSON) throws JSONException {
        this.id_user = objetoJSON.getString("id_user");
        this.nombre = objetoJSON.getString("nombre");
        this.apellido = objetoJSON.getString("apellido");
        this.email = objetoJSON.getString("email");
        this.telefono = objetoJSON.getString("telefono");
        this.contraseña = objetoJSON.getString("contraseña");

    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
