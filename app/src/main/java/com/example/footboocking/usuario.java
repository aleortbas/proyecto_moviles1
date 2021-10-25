package com.example.footboocking;

import org.json.JSONException;
import org.json.JSONObject;

public class usuario {

    String id_user, nombreDB, apellidoDB, emailDB, telefonoDB, contraseñaDB;
    int rol;

    public usuario(JSONObject objetoJSON) throws JSONException {
        this.id_user = objetoJSON.getString("id");
        this.nombreDB = objetoJSON.getString("nombre");
        this.apellidoDB = objetoJSON.getString("apellido");
        this.emailDB = objetoJSON.getString("email");
        this.telefonoDB = objetoJSON.getString("telefono");
        this.contraseñaDB = objetoJSON.getString("clave");
        this.rol = Integer.parseInt(objetoJSON.getString("rol"));

    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String nombreDB) {
        this.id_user = id_user;
    }

    public String getNombreDB() {
        return  nombreDB;
    }

    public void setNombreDB(String nombreDB) {
        this.nombreDB = nombreDB;
    }

    public String getApellidoDB() {
        return  apellidoDB;
    }

    public void setApellidoDB(String apellidoDB) {
        this.apellidoDB = apellidoDB;
    }

    public String getEmailDB() {
        return  emailDB;
    }

    public void setEmailDB(String emailDB) {
        this.emailDB = emailDB;
    }

    public String getTelefonoDB() {
        return  telefonoDB;
    }

    public void setTelefonoDB(String telefonoDB) {
        this.telefonoDB = telefonoDB;
    }

    public String getContraseñaDB() {
        return  contraseñaDB;
    }

    public void setContraseñaDB(String contraseñaDB) {
        this.contraseñaDB = contraseñaDB;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
}
