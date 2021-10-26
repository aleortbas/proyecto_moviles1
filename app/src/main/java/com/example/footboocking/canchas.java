package com.example.footboocking;


import org.json.JSONException;
import org.json.JSONObject;

public class canchas {

        String id, nombre, precio, idUser, numero_canchas,direccion;

    public canchas (JSONObject objetoJSON) throws JSONException {
        this.id = objetoJSON.getString("id");
        this.nombre = objetoJSON.getString("nombre");
        this.precio = objetoJSON.getString("precio_hora");
        this.idUser = objetoJSON.getString("id_usuario");
        this.numero_canchas = objetoJSON.getString("numero_canchas");
        this.direccion = objetoJSON.getString("direccion");
    }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPrecio() {
            return precio;
        }

        public void setPrecio(String precio) {
            this.precio = precio;
        }

        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public String getNumero_canchas() {
            return numero_canchas;
        }

        public void setNumero_canchas(String numero_canchas) {
            this.numero_canchas = numero_canchas;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
    }
