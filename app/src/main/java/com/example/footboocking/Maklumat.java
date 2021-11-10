package com.example.footboocking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Maklumat {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("nombre")
    @Expose
    public String nombre;
    @SerializedName("precio_hora")
    @Expose
    public String precioHora;
    @SerializedName("id_usuario")
    @Expose
    public String idUsuario;
    @SerializedName("numero_canchas")
    @Expose
    public String numeroCanchas;
    @SerializedName("direccion")
    @Expose
    public String direccion;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;

}