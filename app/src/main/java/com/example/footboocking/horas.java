package com.example.footboocking;

import org.json.JSONException;
import org.json.JSONObject;

public class horas {

    String hora, horaFinal, id,id_cancha, estado;

    public horas(JSONObject objetoJSON) throws JSONException {
        this.id = objetoJSON.getString("id");
        this.id_cancha = objetoJSON.getString("id_cancha");
        this.estado = objetoJSON.getString("estado");
        this.hora = objetoJSON.getString("hora");
        this.horaFinal = objetoJSON.getString("hora_final");
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_cancha() {
        return id_cancha;
    }

    public void setId_cancha(String id_cancha) {
        this.id_cancha = id_cancha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
