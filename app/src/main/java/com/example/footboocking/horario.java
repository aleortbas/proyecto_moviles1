package com.example.footboocking;

import org.json.JSONObject;

public class horario {

    int id, idHorario, idCancha, estado;
    String hora, horaFinal;

    public horario(int id, int idHorario, int idCancha, int estado, String hora, String horaFinal) {
        this.id = id;
        this.idHorario = idHorario;
        this.idCancha = idCancha;
        this.estado = estado;
        this.hora = hora;
        this.horaFinal = horaFinal;
    }

    public horario(JSONObject jsonObject) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdCancha() {
        return idCancha;
    }

    public void setIdCancha(int idCancha) {
        this.idCancha = idCancha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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
}
